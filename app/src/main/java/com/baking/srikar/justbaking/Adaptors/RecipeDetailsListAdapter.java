package com.baking.srikar.justbaking.Adaptors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baking.srikar.justbaking.Models.BakingResponse;
import com.baking.srikar.justbaking.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsListAdapter extends RecyclerView.Adapter<RecipeDetailsListAdapter.RecipeDetailsViewHolder> {

    Context context;
    private BakingResponse mBakingList;
    private LayoutInflater mInflater;

    public RecipeDetailsListAdapter(Context context, BakingResponse mBakingList) {
        this.context = context;
        this.mBakingList = mBakingList;
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecipeDetailsListAdapter.RecipeDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.recipe_details_list_layout,parent,false);
        return new RecipeDetailsListAdapter.RecipeDetailsViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailsListAdapter.RecipeDetailsViewHolder holder, int position) {

        holder.stepTv.setText(mBakingList.getSteps().get(position).getShortDescription());
        Log.v("Steps size", String.valueOf(mBakingList.getSteps().size()));
        Log.v("position", String.valueOf(position));

    }

    @Override
    public int getItemCount() {
        return mBakingList.getSteps().size();
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