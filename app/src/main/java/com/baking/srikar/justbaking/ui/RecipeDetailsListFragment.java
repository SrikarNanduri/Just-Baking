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
import android.widget.TextView;

import com.baking.srikar.justbaking.Models.BakingResponse;
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
        /*final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ingredientsRv.setLayoutManager(layoutManager);
        ingredientsRv.setHasFixedSize(true);
        ingredientsAdapter = new IngredientsAdapter(getContext(), ingridentList);
        ingredientsRv.setAdapter(ingredientsAdapter);*/

        return rootView;
    }

    public void ingredients(BakingResponse bakingResponse){
        List<String> ingredientList =new ArrayList<String>();
        for(int i=0; i<bakingResponse.getIngredients().size(); i++){
            String ingridents = bakingResponse.getIngredients().get(i).getIngredient();
            double quantityValue = bakingResponse.getIngredients().get(i).getQuantity();
            String measure = bakingResponse.getIngredients().get(i).getMeasure() ;
            String ingridentsListString;
            if ((quantityValue == Math.floor(quantityValue)) && !Double.isInfinite(quantityValue)) {
                // integer type
                ingridentsListString = ingridents + "(" + (int)quantityValue + " " + measure + ")";
            } else {
                ingridentsListString = ingridents + "(" +  quantityValue + " " + measure + ")";
            }

            ingredientList.add(ingridentsListString);
        }
        Log.v("IngridentsList(0)", ingredientList.get(1));
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
