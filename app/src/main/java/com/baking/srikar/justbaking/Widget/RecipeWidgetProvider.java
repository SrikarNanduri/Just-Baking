package com.baking.srikar.justbaking.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.baking.srikar.justbaking.Models.BakingResponse;
import com.baking.srikar.justbaking.R;
import com.baking.srikar.justbaking.ui.MainActivity;
import com.google.gson.Gson;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private static BakingResponse bakingListModels;
    private static int sizeOfBakingList = 0;
    private static int currentIndexOfIngredient = 0;
    private static RemoteViews views;
    private static int height;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Bundle bundle = appWidgetManager.getAppWidgetOptions(appWidgetId);
        height = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
        views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        getData(context);

        // To launch the application
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_ingrident_name, pendingIntent);

        //  Clicking next button will load next ingredient
        Intent nextIntent = new Intent(context,WidgetService.class);
        nextIntent.setAction(WidgetService.ACTION_INCREMENT);
        nextIntent.putExtra(WidgetService.INTENT_SIZE,sizeOfBakingList);
        nextIntent.putExtra(WidgetService.INTENT_CURRENT_INDEX, currentIndexOfIngredient);
        PendingIntent nextPendingIntent = PendingIntent.getService(context, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.next_button, nextPendingIntent);

        //  Clicking previous button will load previous ingredient
        Intent prevIntent = new Intent(context,WidgetService.class);
        prevIntent.setAction(WidgetService.ACTION_DECREMENT);
        prevIntent.putExtra(WidgetService.INTENT_SIZE,sizeOfBakingList);
        prevIntent.putExtra(WidgetService.INTENT_CURRENT_INDEX, currentIndexOfIngredient);
        PendingIntent prevPendingIntent = PendingIntent.getService(context,0,prevIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.back_button,prevPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        updateAppWidget(context,appWidgetManager,appWidgetId);
    }

    public static void getData(Context context){
        try{
        SharedPreferences prefs = context.getSharedPreferences(WidgetService.PREF_BAKING_LIST, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        sizeOfBakingList = prefs.getInt(WidgetService.PREF_BAKING_LIST_SIZE,0);
        currentIndexOfIngredient = prefs.getInt(WidgetService.PREF_BAKING_CURRENT_INDEX,0);

        if(sizeOfBakingList > 0 && currentIndexOfIngredient < sizeOfBakingList && currentIndexOfIngredient > -1){
            String jsonBakingList = prefs.getString(String.valueOf(currentIndexOfIngredient), "");
            bakingListModels = gson.fromJson(jsonBakingList,BakingResponse.class);

            if(bakingListModels != null){
                views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
                views.setTextViewText(R.id.widget_ingrident_name,bakingListModels.getName());
            }
        }

        String ingredientList = "";
        if(height <= 200){
            ingredientList = "";
        }else{
            for(int i=0;i<bakingListModels.getIngredients().size();i++){
                if(height <= 450){
                    if(i != 0){
                        ingredientList += ", ";
                    }
                    ingredientList += bakingListModels.getIngredients().get(i).getIngredient();
                }else{
                    ingredientList += bakingListModels.getIngredients().get(i).getIngredient()+"\n";
                }
            }
        }
        views.setTextViewText(R.id.widget_ingrident_list,ingredientList);


        if(currentIndexOfIngredient == 0){
            views.setViewVisibility(R.id.back_button, View.INVISIBLE);
        }else{
            views.setViewVisibility(R.id.back_button, View.VISIBLE);
        }

        if(currentIndexOfIngredient == sizeOfBakingList-1){
            views.setViewVisibility(R.id.next_button, View.INVISIBLE);
        }else{
            views.setViewVisibility(R.id.next_button, View.VISIBLE);
        }
    }catch (Exception e){
        e.printStackTrace();
        }
}
}

