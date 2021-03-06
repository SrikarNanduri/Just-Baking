package com.baking.srikar.justbaking.ui;


import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.baking.srikar.justbaking.R;


public class StepDetailsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

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

}
