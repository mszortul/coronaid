package com.coronaid.coronaid;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationManagerCompat;

public class MyNewIntentService extends JobIntentService {
    private static final int NOTIFICATION_ID = 3;

    public MyNewIntentService() { }

    @Override
    protected void onHandleWork(Intent intent) {
        RemoteViews contentView;

        String lang = intent.getStringExtra("lang");

        if (lang.equals("tr")) {
            contentView = new RemoteViews(getPackageName(), R.layout.custom_notification);
        } else {
            contentView = new RemoteViews(getPackageName(), R.layout.custom_notification_en);
        }
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContent(contentView);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.app_logo_crop);
        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);
    }
}