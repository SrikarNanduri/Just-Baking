package com.baking.srikar.justbaking.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.baking.srikar.justbaking.Adaptors.RecipeListAdapter;
import com.baking.srikar.justbaking.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    /*@BindView(R.id.home_rv)
    RecyclerView homeRv;*/
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    //RecipeListAdapter recipeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Baking Time");
     //   bakingFeed();
    }

/*    public void generateBakingList(List<BakingResponse> bakingResponses) {
        homeRv.setLayoutManager(new LinearLayoutManager(this));
        homeRv.setHasFixedSize(true);
        recipeListAdapter = new RecipeListAdapter(this, bakingResponses);
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
    }*/
}
