package com.itu.tourisme;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreferences {

    private static final String KEY_NOTIFICATION_ENABLED = "notification_enabled";

    private SharedPreferences preferences;

    public AppPreferences(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isNotificationEnabled() {
        return preferences.getBoolean(KEY_NOTIFICATION_ENABLED, false);
    }
}