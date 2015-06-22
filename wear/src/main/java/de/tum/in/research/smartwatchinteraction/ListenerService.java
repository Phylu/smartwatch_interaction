package de.tum.in.research.smartwatchinteraction;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.Vibrator;
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

            final Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            long[] pattern = { 0, 1 };
            v.vibrate( pattern, -1 );

            Intent notificationIntent = new Intent(this, SwipeNotificationActivity.class);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(notificationIntent);

        } else {
            super.onMessageReceived(messageEvent);
        }
    }
}