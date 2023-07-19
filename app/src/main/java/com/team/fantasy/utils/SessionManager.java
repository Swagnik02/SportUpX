package com.team.fantasy.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team.fantasy.Bean.UserDetails;

import java.lang.reflect.Type;



public class SessionManager {

    static String User = "User";
    static String Answer = "Answer";
    static String session = "session";
    static String image="image";
    private static SharedPreferences.Editor prefsEditor;
    public UserDetails getUser(Context mContext) {
        SharedPreferences pref = mContext.getSharedPreferences(User, Context.MODE_PRIVATE);
        String userJSONString = pref.getString("users", "");
        if (userJSONString == null)
            return null;
        Type type = new TypeToken<UserDetails>() {
        }.getType();
        UserDetails user = new Gson().fromJson(userJSONString, type);
        return user;
    }

    public void setUser(Context mContext, UserDetails user) {
        SharedPreferences pref = mContext.getSharedPreferences(User, Context.MODE_PRIVATE);
        prefsEditor = pref.edit();
        if (user == null)
            prefsEditor.putString("users", null);
        else {
            String userJSONString = new Gson().toJson(user);
            prefsEditor.putString("users", userJSONString);
        }
        prefsEditor.commit();
    }

    public void setImage(Context mContext, String user) {
        SharedPreferences pref = mContext.getSharedPreferences(image, Context.MODE_PRIVATE);
        prefsEditor = pref.edit();
        if (image == null)
            prefsEditor.putString("image", null);
        else {

            prefsEditor.putString("image", user);
        }
        prefsEditor.commit();
    }



}
