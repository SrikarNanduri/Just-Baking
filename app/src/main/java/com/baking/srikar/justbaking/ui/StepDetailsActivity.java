package com.baking.srikar.justbaking.ui;

import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.baking.srikar.justbaking.Models.Step;
import com.baking.srikar.justbaking.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsActivity extends AppCompatActivity {

    @BindView(R.id.my_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Baking Time");

        String steps = getIntent().getStringExtra("stepsList");
        Log.v("StepsData", steps);
        int position = getIntent().getIntExtra("position",0);

        //every time if there are any configuration changes then the fragement is getting recreated and added to stack,
        //so to avoid that we need to check if fragment is created in the host activity by checking savedInstanceState
        //Even if you don't override onSaveInstanceState in the activity, the savedInstanceState parameter will still be
        //non-null when restoring an Activity. It'll just be an empty Bundle.
        if(savedInstanceState  == null) {
            StepDetailsListFragment stepDetailsListFragment = new StepDetailsListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("stepposition", position);
            bundle.putString("Steps", steps);

            stepDetailsListFragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.steps_details_fragment_body_part, stepDetailsListFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
