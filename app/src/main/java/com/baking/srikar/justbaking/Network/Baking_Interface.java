package com.baking.srikar.justbaking.Network;


import com.baking.srikar.justbaking.Models.BakingResponse;
import com.baking.srikar.justbaking.Models.Ingredient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Baking_Interface {

    @GET("baking.json")
    Call<List<BakingResponse>> getBakingList();
}
