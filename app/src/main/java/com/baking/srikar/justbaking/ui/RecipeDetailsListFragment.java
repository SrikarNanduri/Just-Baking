package com.baking.srikar.justbaking.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.srikar.justbaking.Models.BakingResponse;
import com.baking.srikar.justbaking.R;
import com.google.gson.Gson;

public class RecipeDetailsListFragment extends Fragment {
    public RecipeDetailsListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_details_fragment_body, container, false);
        String bakingList = getArguments().getString("bakinglist");
        Gson gson = new Gson();
        BakingResponse bakingResponse = gson.fromJson(bakingList, BakingResponse.class);
        Log.v("Json Data", bakingResponse.getName());
        
        return rootView;
    }
}
