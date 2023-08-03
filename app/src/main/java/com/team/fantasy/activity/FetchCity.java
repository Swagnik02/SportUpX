package com.team.fantasy.activity;

import static com.team.fantasy.APICallingPackage.Constants.GETCITYTYPE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.APICallingPackage.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchCity implements ResponseManager {

    private Context mContext;
    private List<String> cityList;

    public FetchCity(Context context) {
        this.mContext = context;
        this.cityList = new ArrayList<>();
    }

    public void callFetchCities(String stateId, boolean isShowLoader) {
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

                // Process the cityArray to get the city names and update the list
                cityList.clear();
                for (int i = 0; i < cityArray.length(); i++) {
                    JSONObject cityObject = cityArray.getJSONObject(i);
                    String cityName = cityObject.getString("name");
                    cityList.add(cityName);
                }

                // Save the city list to SharedPreferences
                saveCityListToSharedPreferences(cityList);

                // Add log to check if city list is being stored
                Log.d("FetchCity", "City list stored: " + cityList);

//                Log.d("APIResponse", "Type: " + type + ", Message: " + message + ", Result: " + result);


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
        // Handle the error response
        Toast.makeText(mContext, "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    private void saveCityListToSharedPreferences(List<String> cityList) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("CityListPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JSONArray jsonArray = new JSONArray(cityList);
        editor.putString("cityList", jsonArray.toString());
        editor.apply();
    }

    public List<String> getCityListFromSharedPreferences() {
        List<String> cityList = new ArrayList<>();
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("CityListPrefs", Context.MODE_PRIVATE);
        String cityListJson = sharedPreferences.getString("cityList", "[]");
        try {
            JSONArray jsonArray = new JSONArray(cityListJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                cityList.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cityList;
    }


}
