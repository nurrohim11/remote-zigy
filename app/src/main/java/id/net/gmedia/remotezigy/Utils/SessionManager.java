package id.net.gmedia.remotezigy.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "GmediaTV";

    // All Shared Preferences Keys
    public static final String TAG_LINK = "link";
    public static final String TAG_FCMID ="fcm_id";
    public static final String TAG_COUNT_SLIDER = "count_slider";

    // Constructor
    public SessionManager(Context context){
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveFcmId(String fcm_id){

        editor.putString(TAG_FCMID, fcm_id);

        // commit changes
        editor.commit();
    }

    public void saveCountSlider(int count_slider){
        editor.putInt(TAG_COUNT_SLIDER,count_slider);
        editor.commit();
    }

    public void saveLink(String link){

        editor.putString(TAG_LINK, link);

        // commit changes
        editor.commit();
    }

    public String getLink(){
        return pref.getString(TAG_LINK, "");
    }

    public String getFcmid(){
        return pref.getString(TAG_FCMID, "");
    }

    public int getCountSlider(){
        return pref.getInt(TAG_COUNT_SLIDER, 0);
    }
}
