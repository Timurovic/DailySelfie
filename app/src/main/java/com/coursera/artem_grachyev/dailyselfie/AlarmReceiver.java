package com.coursera.artem_grachyev.dailyselfie;

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
    // AlarmReceiver alarm = new AlarmReceiver();
    public static int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {

     /*   if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            alarm.setAlarm(context);
        }

     */

        setAlarm(context);
    }

    public void setAlarm(Context context) {
        Intent notificationIntent = new Intent(context, MainActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText("Time for another selfie")
                .setTicker("Time for another selfie").setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setAutoCancel(true)
                .setSmallIcon(R.drawable.ds);

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {

            nm.notify(NOTIFICATION_ID, builder.build());
        }
        else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH ){
            nm.notify(NOTIFICATION_ID, builder.getNotification());
        }
    }

}
