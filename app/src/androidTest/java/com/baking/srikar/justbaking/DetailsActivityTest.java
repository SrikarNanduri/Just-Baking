package com.baking.srikar.justbaking;


import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.baking.srikar.justbaking.Models.BakingResponse;
import com.baking.srikar.justbaking.Models.Ingredient;
import com.baking.srikar.justbaking.Models.Step;
import com.baking.srikar.justbaking.ui.DetailsActivity;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DetailsActivityTest {
    List<Ingredient> ingredients =new ArrayList<>();
    List<Step> steps = new ArrayList<>();
    BakingResponse bakingResponse;


    @Rule
    public final ActivityTestRule<DetailsActivity> mActivityRule = new ActivityTestRule<DetailsActivity>(DetailsActivity.class,false,false);


    @Before
    public void fakeData(){
        ingredients.add(new Ingredient((double) 2,"CUP","Cheese"));
        steps.add(new Step(0,"Recipe Introduction","Recipe Introduction","https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4",null));
        bakingResponse = new BakingResponse(0,"Nutella Pie",ingredients,steps,8,null);
        Intent intent = new Intent();
        Gson gson = new Gson();
        String bakingList = gson.toJson(bakingResponse);
        intent.putExtra("bakinglistobj", bakingList);
        mActivityRule.launchActivity(intent);
        mActivityRule.getActivity().getFragmentManager().beginTransaction();
    }



    @Test
    public void selectingRecipe_openCorrespondingDescription(){
        onView(withId(R.id.placeholder_tv)).check(matches(withText("Ingredients:")));
        onView(withId(R.id.recipe_steps_rv)).check(matches(hasDescendant(withText("Recipe Introduction"))));
        onView(withId(R.id.recipe_steps_rv)).perform(actionOnItem(hasDescendant(withText("Recipe Introduction")), click()));
    }

}
