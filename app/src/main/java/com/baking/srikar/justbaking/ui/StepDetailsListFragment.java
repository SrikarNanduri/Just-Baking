package com.baking.srikar.justbaking.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baking.srikar.justbaking.Models.Step;
import com.baking.srikar.justbaking.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsListFragment extends Fragment{

    @BindView(R.id.stepDetail_tv)
    TextView stepsTv;

    public StepDetailsListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.steps_details_fragment_body, container, false);
        ButterKnife.bind(this, rootView);
        Step steps = getArguments().getParcelable("Steps");
        stepsTv.setText(steps.getDescription());
        return rootView;
    }
}
