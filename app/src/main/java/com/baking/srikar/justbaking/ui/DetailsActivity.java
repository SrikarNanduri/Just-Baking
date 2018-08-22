package com.baking.srikar.justbaking.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.baking.srikar.justbaking.Models.BakingResponse;
import com.baking.srikar.justbaking.R;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.my_toolbar)
    Toolbar mToolbar;
    private boolean isTablet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Baking Time");

        String  bakinglist = getIntent().getStringExtra("bakinglistobj");
        Bundle bundle = new Bundle();
        bundle.putString("bakinglist", bakinglist);

        if(savedInstanceState  == null) {
            isTablet = getResources().getBoolean(R.bool.is_tablet);
            if (isTablet) { //it's a tablet
                RecipeDetailsListFragment fragment = new RecipeDetailsListFragment();
                StepDetailsListFragment stepDetailsListFragment = new StepDetailsListFragment();
                fragment.setArguments(bundle);

                String steps = getIntent().getStringExtra("stepsList");
                int position = getIntent().getIntExtra("position",0);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("stepposition", position);
                bundle2.putString("Steps", steps);
                stepDetailsListFragment.setArguments(bundle2);


                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.container1, fragment)
                        .commit();

                fragmentManager.beginTransaction()
                        .add(R.id.container2, stepDetailsListFragment)
                        .commit();

            } else { //it's a phone, not a tablet

                RecipeDetailsListFragment fragment = new RecipeDetailsListFragment();
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.recipe_details_fragment_body_part, fragment)
                        .commit();
            }
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
