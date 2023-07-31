package com.itu.tourisme;


import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class NotificationService extends IntentService {

    private static final String CHANNEL_ID = "channel_id";
    private static final int NOTIFICATION_ID = 1;

    // Intervalle de 10 secondes en millisecondes
    private static final long INTERVAL_10_SECONDS = 10000;

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        showNotification();
    }

    private void showNotification() {
        // Assurez-vous que le contenu de la notification est correctement configuré
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Notification")
                .setContentText("Ceci est une notification périodique toutes les 10 secondes.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Assurez-vous que le PendingIntent est correctement configuré
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentIntent(pendingIntent);

        // Créez et affichez la notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";
            String description = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = builder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private void startRepeatingNotifications() {
        long triggerAtMillis = System.currentTimeMillis() + INTERVAL_10_SECONDS;
        long intervalMillis = INTERVAL_10_SECONDS;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                new Intent(this, NotificationReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, intervalMillis, pendingIntent);
    }

    // Le reste du code du service...

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        startRepeatingNotifications();
        return super.onStartCommand(intent, flags, startId);
    }
}