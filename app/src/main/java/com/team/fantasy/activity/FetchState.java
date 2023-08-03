package com.team.fantasy.activity;

        import static com.team.fantasy.APICallingPackage.Config.GETSTATE;
        import static com.team.fantasy.APICallingPackage.Constants.GETSTATETYPE;

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

public class FetchState implements ResponseManager {

    private Context mContext;
    private List<String> stateList;

    public FetchState(Context context) {
        this.mContext = context;
        this.stateList = new ArrayList<>();
    }

    public void callFetchStates(String countryId, boolean isShowLoader) {
        APIRequestManager apiRequestManager = new APIRequestManager(mContext);
        try {
            apiRequestManager.callAPI(Config.GETSTATE, createRequestJson(countryId), mContext, (Activity) mContext, GETSTATETYPE,
                    isShowLoader, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONObject createRequestJson(String countryId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("country_id", countryId);
        return jsonObject;
    }

    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {
        if (type.equals(GETSTATETYPE)) {
            try {
                // Parse the JSON response and get the list of states
                JSONArray stateArray = result.getJSONArray("data");

                // Process the stateArray to get the state names and update the list
                stateList.clear();
                for (int i = 0; i < stateArray.length(); i++) {
                    JSONObject stateObject = stateArray.getJSONObject(i);
                    String stateName = stateObject.getString("name");
                    stateList.add(stateName);
                }

                // Save the state list to SharedPreferences
                saveStateListToSharedPreferences(stateList);

                // Add log to check if state list is being stored
                Log.d("FetchState", "State list stored: " + stateList);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            // Handle the error message when states are not found
            // You can show an error message or take appropriate actions
        }
    }

    @Override
    public void onError(Context mContext, String type, String message) {
        // Handle the error response
        Toast.makeText(mContext, "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    private void saveStateListToSharedPreferences(List<String> stateList) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("StateListPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JSONArray jsonArray = new JSONArray(stateList);
        editor.putString("stateList", jsonArray.toString());
        editor.apply();
    }

    public List<String> getStateListFromSharedPreferences() {
        List<String> stateList = new ArrayList<>();
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("StateListPrefs", Context.MODE_PRIVATE);
        String stateListJson = sharedPreferences.getString("stateList", "[]");
        try {
            JSONArray jsonArray = new JSONArray(stateListJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                stateList.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stateList;
    }
}
