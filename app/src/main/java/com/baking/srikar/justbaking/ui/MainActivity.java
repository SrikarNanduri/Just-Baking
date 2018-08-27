package com.baking.srikar.justbaking.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.baking.srikar.justbaking.Config.SimpleIdlingResource;
import com.baking.srikar.justbaking.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    @Nullable
    private SimpleIdlingResource simpleIdlingResource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Baking Time");
        if(savedInstanceState  == null) {
            RecipeListFragment recipeListFragment = new RecipeListFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_fragment_body_part, recipeListFragment)
                    .commit();
        }
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (simpleIdlingResource == null) {
            simpleIdlingResource = new SimpleIdlingResource();
        }
        return simpleIdlingResource;
    }
}
