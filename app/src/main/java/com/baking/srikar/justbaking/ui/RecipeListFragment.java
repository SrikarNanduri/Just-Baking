package com.baking.srikar.justbaking.ui;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baking.srikar.justbaking.Adaptors.RecipeListAdapter;
import com.baking.srikar.justbaking.Config.SimpleIdlingResource;
import com.baking.srikar.justbaking.Models.BakingResponse;
import com.baking.srikar.justbaking.Network.ApiClient;
import com.baking.srikar.justbaking.Network.Baking_Interface;
import com.baking.srikar.justbaking.Network.ConnectivityReceiver;
import com.baking.srikar.justbaking.Network.MyApplication;
import com.baking.srikar.justbaking.R;
import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class RecipeListFragment extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListener{

    private static final String TAG = RecipeListFragment.class.getSimpleName();
    private final String PREF_BAKING_LIST_SIZE = "baking_list_size";
    private final String PREF_BAKING_LIST = "BAKING_LIST";
    MainActivity activity;

    @BindView(R.id.recipe_rv)
    RecyclerView homeRv;

    RecipeListAdapter recipeListAdapter;
    Integer[] images = {
            R.drawable.nutella,
            R.drawable.brownies,
            R.drawable.yellowcake,
            R.drawable.cheese
    };

    private boolean isTablet;
    private int columns;

    @Nullable
    private SimpleIdlingResource simpleIdlingResource;

    public RecipeListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.recipe_fragment_body, container, false);
        ButterKnife.bind(this, rootView);
        isTablet = getResources().getBoolean(R.bool.is_tablet);

        activity = (MainActivity) getActivity();
        simpleIdlingResource = (SimpleIdlingResource) activity.getIdlingResource();


        if (isTablet) { //it's a tablet

            Configuration newConfig = getResources().getConfiguration();
            if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
                columns = 3;
            } else {
                if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
                    columns = 2;
                }
            }
        } else { //it's a phone, not a tablet

            Configuration newConfig = getResources().getConfiguration();
            if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
                columns = 2;
            } else {
                if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
                    columns = 1;
                }
            }
        }
        checkConnection();
        return  rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        checkConnection();
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        connection(isConnected);

    }

    // Checking the status
    private void connection(boolean isConnected){
        if(isConnected){
           bakingFeed();
        } else {
            Toast.makeText(getContext(), "Network Not Available", Toast.LENGTH_LONG).show();
        }
    }

    public void generateBakingList(List<BakingResponse> bakingResponses) {
        saveToSharedPreferences(bakingResponses);
        homeRv.setLayoutManager(new GridLayoutManager(getContext(),columns));
        homeRv.setHasFixedSize(true);
        recipeListAdapter = new RecipeListAdapter(getContext(), bakingResponses, images);
        homeRv.setAdapter(recipeListAdapter);

    }

    private void bakingFeed() {

        if (simpleIdlingResource != null) {
            simpleIdlingResource.setIdleState(false);
        }
        Baking_Interface apiService = ApiClient.getClient().create(Baking_Interface.class);
        Call<List<BakingResponse>> call = apiService.getBakingList();
        Log.v("URL", call.request().url() + "");
        call.enqueue(new Callback<List<BakingResponse>>() {
            @Override
            public void onResponse(Call<List<BakingResponse>> call, Response<List<BakingResponse>> response) {
                if (response.isSuccessful()) {
                    List<BakingResponse> bakingResponses = response.body();
                    Log.v("Baking Response", bakingResponses.get(0).getName());
                    generateBakingList(bakingResponses);
                    if (simpleIdlingResource != null) {
                        simpleIdlingResource.setIdleState(true);
                    }
                } else {

                    switch (response.code()) {
                        case 400:
                            Toast.makeText(getContext(), "Bad Request.", Toast.LENGTH_LONG).show();
                            break;
                        case 404:
                            Toast.makeText(getContext(), "Not Found.", Toast.LENGTH_LONG).show();
                            break;
                        case 415:
                            Toast.makeText(getContext(), "Unsupported Media Type.", Toast.LENGTH_LONG).show();
                            break;
                        case 500:
                            Toast.makeText(getContext(), "Internal Server Error", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(getContext(), "Service broke status code is: " + response.code(), Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BakingResponse>> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        connection(isConnected);
    }


    private void saveToSharedPreferences(List<BakingResponse> bakingListModels){

        SharedPreferences.Editor editor = activity.getSharedPreferences(PREF_BAKING_LIST, MODE_PRIVATE).edit();
        for(int i=0;i<bakingListModels.size();i++){
            Gson gson = new Gson();
            String json = gson.toJson(bakingListModels.get(i));
            editor.putString(String.valueOf(i), json);
        }
        editor.putInt(PREF_BAKING_LIST_SIZE,bakingListModels.size());
        editor.apply();
    }

}
