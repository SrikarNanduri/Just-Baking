package com.baking.srikar.justbaking;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.baking.srikar.justbaking.ui.DetailsActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DetailsActivityTest {

    @Rule
    public final ActivityTestRule<DetailsActivity> activityTestRule = new ActivityTestRule<DetailsActivity>(DetailsActivity.class,false,false);



}
