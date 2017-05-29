package com.ellenabeautyproducts.ellenabeautycustomers;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;

/**
 * Created by KELVIN on 26/05/2017.
 */
public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;
    private RequestQueue requestQueue;


    private final static  String SHARED_PREF_NAME = "mysharedpref12";
    private final static  String KEY_USER_ID = "userid";
    private final static String KEY_USER_FNAME = "username";
    private final static String KEY_USER_EMAIL = "useremail";


    private SharedPrefManager(Context context)
    {
        mCtx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context)
    {
        if(mInstance==null)
        {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }
    public boolean userLogin(int user_id,String fname,String email)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID,user_id);
        editor.putString(KEY_USER_FNAME,fname);
        editor.putString(KEY_USER_EMAIL,email);
        editor.apply();
        return true;
    }
    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USER_FNAME,null)!=null)
        {
              return true;
        }
        return false;
    }
    public boolean logout()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
    public String getKeyUserFname()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_FNAME,null);
    }
    public String getKeyUserEmail()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL,null);
    }
    public String getKeyUserID()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ID,null);
    }
}
