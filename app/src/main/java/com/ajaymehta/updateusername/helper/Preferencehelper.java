package com.ajaymehta.updateusername.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;


public class Preferencehelper {  // so that the details coming from server ..i can save it in sharedpreference ..n use it n whole project..
    private static final String UID = "u_id";


    private SharedPreferences sharePresfs;
    private SharedPreferences.Editor editor;
    private Context context;



    public Preferencehelper(Context context) {
        this.sharePresfs = context.getSharedPreferences(Param.SHAREDPREF, Context.MODE_PRIVATE);
        this.context = context;
        this.editor = this.sharePresfs.edit();
    }

    public int getUid() {
        return sharePresfs.getInt(UID, -1);
    }

    public void setUid(int id) {
        editor = sharePresfs.edit();
        editor.putInt(UID, id);
        editor.commit();
    }


}
