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
import android.widget.TextView;

import com.baking.srikar.justbaking.Adaptors.RecipeDetailsListAdapter;
import com.baking.srikar.justbaking.Adaptors.RecipeListAdapter;
import com.baking.srikar.justbaking.Config.ItemClickSupport;
import com.baking.srikar.justbaking.Models.BakingResponse;
import com.baking.srikar.justbaking.Models.Step;
import com.baking.srikar.justbaking.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsListFragment extends Fragment {

    @BindView(R.id.ingredients_tv)
    TextView ingredientsTv;

    @BindView(R.id.recipe_steps_rv)
    RecyclerView stepsRv;

    RecipeDetailsListAdapter recipeDetailsListAdapter;

    public RecipeDetailsListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_details_fragment_body, container, false);
        ButterKnife.bind(this, rootView);
        String bakingList = getArguments().getString("bakinglist");
        Gson gson = new Gson();
        BakingResponse bakingResponse = gson.fromJson(bakingList, BakingResponse.class);
        Log.v("Json Data", bakingResponse.getName());
        ingredients(bakingResponse);
        steps(bakingResponse);
        return rootView;
    }

    public void steps(BakingResponse bakingResponse){
        stepsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        stepsRv.setHasFixedSize(true);
        stepsRv.setNestedScrollingEnabled(false);
        final List<Step> stepsList = bakingResponse.getSteps();
        recipeDetailsListAdapter = new RecipeDetailsListAdapter(getContext(), stepsList);
        stepsRv.setAdapter(recipeDetailsListAdapter);
       /* ItemClickSupport.addTo(stepsRv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                ((DetailsActivity) getActivity()).playerData(position, stepsList);
            }
        });*/

    }

    public void ingredients(BakingResponse bakingResponse){
        List<String> ingredientList =new ArrayList<String>();

        for(int i=0; i<bakingResponse.getIngredients().size(); i++){
            String ingridents = bakingResponse.getIngredients().get(i).getIngredient();
            double quantityValue = bakingResponse.getIngredients().get(i).getQuantity();
            String measure = bakingResponse.getIngredients().get(i).getMeasure() ;

            String ingridentsListString;
            if ((quantityValue == Math.floor(quantityValue)) && !Double.isInfinite(quantityValue)) {
                ingridentsListString = ingridents + "(" + (int)quantityValue + " " + measure + ")";
            } else {
                ingridentsListString = ingridents + "(" +  quantityValue + " " + measure + ")";
            }
            ingredientList.add(ingridentsListString);
        }

        StringBuilder result = new StringBuilder();
        for (int j=0; j<ingredientList.size() - 1; j++) {
            result.append(ingredientList.get(j));
            result.append(",");
            result.append(System.lineSeparator());
        }
        result.append(ingredientList.get(ingredientList.size()-1));
        Log.v("whole list", String.valueOf(result));

        ingredientsTv.setText(String.valueOf(result));
    }

}
