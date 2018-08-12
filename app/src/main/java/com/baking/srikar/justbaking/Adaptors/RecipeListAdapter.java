package com.baking.srikar.justbaking.Adaptors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baking.srikar.justbaking.Models.BakingResponse;
import com.baking.srikar.justbaking.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.BakingViewHolder> {

    Context context;
    private List<BakingResponse> mBakingList;
    private Integer[] images;
    private LayoutInflater mInflater;

    public RecipeListAdapter(Context context, List<BakingResponse> mBakingList, Integer[] images) {
        this.context = context;
        this.mBakingList = mBakingList;
        this.images = images;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecipeListAdapter.BakingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.recipe_list_layout,parent,false);
        return new BakingViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapter.BakingViewHolder holder, int position) {

        Picasso.with(context).load(String.valueOf(images))
                .placeholder(R.drawable.ic_action_placeholder_light)
                .into(holder.recipeIv);
        holder.recipeNameTv.setText(mBakingList.get(position).getName());
        holder.recipeServings.setText(Integer.toString(mBakingList.get(position).getServings()));
    }

    @Override
    public int getItemCount() {
        return mBakingList.size();
    }

class BakingViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.recipe_list_iv)
    ImageView recipeIv;
    @BindView(R.id.recipe_name_tv)
    TextView recipeNameTv;
    @BindView(R.id.recipe_servings_tv)
    TextView recipeServings;

    public BakingViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
}
