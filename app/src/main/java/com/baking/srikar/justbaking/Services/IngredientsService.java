package com.baking.srikar.justbaking.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.baking.srikar.justbaking.Models.BakingResponse;
import com.baking.srikar.justbaking.Models.Step;

public class IngredientsService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    public static final String ACTION_SHOW_INGREDIENTS ="com.baking.srikar.justbaking.action.show_ingredients";


    public IngredientsService(String name) {
        super(name);
    }
    public static void startActionIngredients(Context context) {
        Intent intent = new Intent(context, IngredientsService.class);
        intent.setAction(ACTION_SHOW_INGREDIENTS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SHOW_INGREDIENTS.equals(action)) {

            }
        }
    }

    public void handleActionShowIngredients(StringBuilder result){


    }
}
