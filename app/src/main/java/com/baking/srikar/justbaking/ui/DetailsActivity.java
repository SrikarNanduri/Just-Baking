package com.baking.srikar.justbaking.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.baking.srikar.justbaking.Models.BakingResponse;
import com.baking.srikar.justbaking.Models.Step;
import com.baking.srikar.justbaking.R;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.my_toolbar)
    Toolbar mToolbar;
    private boolean isTablet;
    String  bakinglist;
    Bundle bundle;
    Bundle bundle2;

    RecipeDetailsListFragment fragment;
    StepDetailsListFragment stepDetailsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Baking Time");

   bakinglist = getIntent().getStringExtra("bakinglistobj");
            bundle = new Bundle();
            bundle.putString("bakinglist", bakinglist);

        if(savedInstanceState  == null) {

            isTablet = getResources().getBoolean(R.bool.is_tablet);
            if (isTablet) { //it's a tablet

                 fragment = new RecipeDetailsListFragment();
                 stepDetailsListFragment = new StepDetailsListFragment();

                fragment.setArguments(bundle);


                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.container1, fragment)
                        .commit();

                fragmentManager.beginTransaction()
                        .add(R.id.container2, stepDetailsListFragment)
                        .commit();

            } else { //it's a phone, not a tablet

                fragment = new RecipeDetailsListFragment();
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.recipe_details_fragment_body_part, fragment)
                        .commit();
            }

        } /*else {
            bakinglist = savedInstanceState.getString("bakingResponseList");
        }*/


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("bakingResponseList", bakinglist);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void playerData(int position, List<Step> step){
        stepDetailsListFragment.getPlayerData(position, step);
    }
}
