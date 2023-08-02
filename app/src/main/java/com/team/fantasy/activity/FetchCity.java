package com.team.fantasy.activity;

import static com.team.fantasy.APICallingPackage.Constants.GETCITYTYPE;

import android.app.Activity;
import android.content.Context;

import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.APICallingPackage.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchCity implements ResponseManager {
    private ResponseManager responseManager;

    public void callFetchCities(Context mContext, String stateId, boolean isShowLoader, ResponseManager responseManager) {
        this.responseManager = responseManager;
        APIRequestManager apiRequestManager = new APIRequestManager(mContext);
        try {
            apiRequestManager.callAPI(Config.GETCITY, createRequestJson(stateId), mContext, (Activity) mContext, GETCITYTYPE,
                    isShowLoader, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONObject createRequestJson(String stateId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state_id", stateId);
        return jsonObject;
    }

    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {
        if (type.equals(GETCITYTYPE)) {
                try {
                    // Parse the JSON response and get the list of cities
                    JSONArray cityArray = result.getJSONArray("data");

                    // Process the cityArray to get the city names and update your UI accordingly
                    List<String> cityNames = new ArrayList<>();
                    for (int i = 0; i < cityArray.length(); i++) {
                        JSONObject cityObject = cityArray.getJSONObject(i);
                        String cityName = cityObject.getString("name");
                        cityNames.add(cityName);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // Handle the error message when cities are not found
                // You can show an error message or take appropriate actions
            }
        }


    @Override
    public void onError(Context mContext, String type, String message) {
        // Handle the error response and pass it back to the calling class
        responseManager.onError(mContext, type, message);
    }
}
