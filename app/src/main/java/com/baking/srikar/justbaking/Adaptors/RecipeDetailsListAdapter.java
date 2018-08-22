package com.baking.srikar.justbaking.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baking.srikar.justbaking.Models.BakingResponse;
import com.baking.srikar.justbaking.Models.Step;
import com.baking.srikar.justbaking.R;
import com.baking.srikar.justbaking.ui.DetailsActivity;
import com.baking.srikar.justbaking.ui.StepDetailsActivity;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsListAdapter extends RecyclerView.Adapter<RecipeDetailsListAdapter.RecipeDetailsViewHolder> {

    Context context;
    private List<Step> mStepsList;
    private LayoutInflater mInflater;
    private boolean isTablet;

    public RecipeDetailsListAdapter(Context context,  List<Step> mStepsList) {
        this.context = context;
        this.mStepsList = mStepsList;
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecipeDetailsListAdapter.RecipeDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.recipe_details_list_layout,parent,false);
        return new RecipeDetailsListAdapter.RecipeDetailsViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailsListAdapter.RecipeDetailsViewHolder holder, final int position) {

        holder.stepTv.setText(mStepsList.get(position).getShortDescription());
       // Log.v("Steps size", String.valueOf(mStepsList.size()));
       // Log.v("position", String.valueOf(position));
        holder.stepTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isTablet = context.getResources().getBoolean(R.bool.is_tablet);
                if (isTablet) { //it's a tablet


                    Intent i = new Intent(context, DetailsActivity.class);
                    Gson gson = new Gson();
                    String stepsList = gson.toJson(mStepsList);
                    i.putExtra("stepsList",  stepsList);
                    i.putExtra("position", position);
                    context.startActivity(i);

                } else { //it's a phone, not a tablet

                    Intent i = new Intent(context, StepDetailsActivity.class);
                    Gson gson = new Gson();
                    String stepsList = gson.toJson(mStepsList);
                    i.putExtra("stepsList",  stepsList);
                    i.putExtra("position", position);
                    context.startActivity(i);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mStepsList.size();
    }

    class RecipeDetailsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.details_list_tv)
        TextView stepTv;

        public RecipeDetailsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}