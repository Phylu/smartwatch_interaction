package de.tum.in.research.smartwatchinteraction;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by janosch on 22.06.15.
 */
public class ListenerService extends WearableListenerService{

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.i("test", "onMessageReceived()");
        if(messageEvent.getPath().equals("Lunch Checker Service")) {
            Log.d("test", "Correct message");
            Intent notificationIntent = new Intent(this, SwipeNotificationActivity.class);
            PendingIntent notificationPendingIntent = PendingIntent.getActivity(
                    this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification.Builder notificationBuilder =
                    new Notification.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .extend(new Notification.WearableExtender()
                                    .setDisplayIntent(notificationPendingIntent)
                                    .setBackground(BitmapFactory.decodeResource(
                                            getResources(), R.drawable.mensa_leopoldstrasse
                                    )));

            // Build the notification and show it
            NotificationManager notificationManager =
                    (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(
                    1, notificationBuilder.build());

        } else {
            super.onMessageReceived(messageEvent);
        }
    }
}