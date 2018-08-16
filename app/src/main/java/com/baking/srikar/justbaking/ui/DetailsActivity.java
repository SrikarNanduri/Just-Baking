package com.baking.srikar.justbaking.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.baking.srikar.justbaking.Models.BakingResponse;
import com.baking.srikar.justbaking.R;
import com.google.gson.Gson;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String  bakinglist = getIntent().getStringExtra("bakinglistobj");
        Bundle bundle = new Bundle();
        bundle.putString("bakinglist", bakinglist);
        RecipeDetailsListFragment fragment = new RecipeDetailsListFragment();
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.recipe_details_fragment_body_part, fragment)
                .commit();
       /* Gson gson = new Gson();
        BakingResponse bakingResponse = gson.fromJson(bakinglist, BakingResponse.class);
        Log.v("Json Data", bakingResponse.getName());*/
    }
}
