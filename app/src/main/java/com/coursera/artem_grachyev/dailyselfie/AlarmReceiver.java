package com.coursera.artem_grachyev.dailyselfie;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by artem_grachyev on 27.11.2014.
 */
public class AlarmReceiver extends BroadcastReceiver{
    AlarmReceiver alarm = new AlarmReceiver();
    public static final int NOTIFICATION_ID = 1;
    private static final long INITIAL_ALARM_DELAY_TWO_MINUTES = 2 * 60 * 1000L;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            alarm.setAlarm(context);
        }

    }

    public void setAlarm(Context context) {
        Intent notificationIntent = new Intent(context, AlarmReceiver.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle("Daily Selfie")
                .setContentText("Time for another selfie")
                .setTicker("Time for another selfie").setWhen(System.currentTimeMillis()) // java.lang.System.currentTimeMillis()
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND).setAutoCancel(true)
                        //    .setSmallIcon(android.R.drawable.ic_menu_camera);
                .setSmallIcon(R.drawable.ds);
        //     .build();

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            nm.notify(NOTIFICATION_ID, builder.build());
        }
        else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH ){
            nm.notify(NOTIFICATION_ID, builder.getNotification());
        }
    }
    
}
