package com.baking.srikar.justbaking.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.srikar.justbaking.Adaptors.RecipeListAdapter;
import com.baking.srikar.justbaking.Models.BakingResponse;
import com.baking.srikar.justbaking.Network.ApiClient;
import com.baking.srikar.justbaking.Network.Baking_Interface;
import com.baking.srikar.justbaking.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListFragment extends Fragment {

    @BindView(R.id.recipe_rv)
    RecyclerView homeRv;

    RecipeListAdapter recipeListAdapter;
    Integer[] images = {
            R.drawable.nutella,
            R.drawable.brownies,
            R.drawable.yellowcake,
            R.drawable.cheese
    };

    public RecipeListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.recipe_fragment_body, container, false);
        ButterKnife.bind(this, rootView);
        bakingFeed();
        return  rootView;
    }

    public void generateBakingList(List<BakingResponse> bakingResponses) {
        homeRv.setLayoutManager(new LinearLayoutManager(getContext()));
        homeRv.setHasFixedSize(true);
        recipeListAdapter = new RecipeListAdapter(getContext(), bakingResponses, images);
        homeRv.setAdapter(recipeListAdapter);

    }

    private void bakingFeed() {
        Baking_Interface apiService = ApiClient.getClient().create(Baking_Interface.class);
        Call<List<BakingResponse>> call = apiService.getBakingList();
        Log.v("URL", call.request().url() + "");
        call.enqueue(new Callback<List<BakingResponse>>() {
            @Override
            public void onResponse(Call<List<BakingResponse>> call, Response<List<BakingResponse>> response) {
                List<BakingResponse> bakingResponses = response.body();
                Log.v("Baking Response", bakingResponses.get(0).getName());
                generateBakingList(bakingResponses);
            }

            @Override
            public void onFailure(Call<List<BakingResponse>> call, Throwable t) {

            }
        });
    }
}
