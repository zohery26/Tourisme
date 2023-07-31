package com.itu.tourisme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    private AppPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appPreferences = new AppPreferences(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, new SettingsFragment())
                    .commit();
        }
    }

    // Méthode pour activer/désactiver les notifications
    public boolean areNotificationsEnabled() {
        return appPreferences.isNotificationEnabled();
    }
}