package de.tum.in.research.smartwatchinteraction.messaging;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import de.tum.in.research.smartwatchinteraction.R;
import de.tum.in.research.smartwatchinteraction.votingactivities.SwipeNotificationActivity;
import de.tum.in.research.smartwatchinteraction.votingactivities.TwoButtonNotificationActivity;

/**
 * Created by janosch on 22.06.15.
 */
public class ListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        String msg = new String(messageEvent.getData());
        Log.i("test", "onMessageReceived(): " + msg);
        if(messageEvent.getPath().equals(getResources().getString(R.string.swipe_notification))) {
            vibrate();
            startSwipeNotification(msg);
        } else if (messageEvent.getPath().equals(getResources().getString(R.string.two_button_notification))) {
            vibrate();
            startTwoButtonNotification(msg);
        } else {
            super.onMessageReceived(messageEvent);
        }
    }

    private void vibrate() {
        final Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = { 0, 100, 0, 100 };
        v.vibrate(pattern, -1);
    }

    private void startActionButtonNotification(String location) {

    }

    private void startSwipeNotification(String location) {
        Intent notificationIntent = new Intent(this, SwipeNotificationActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notificationIntent.putExtra("location", location);

        startActivity(notificationIntent);
    }

    private void startTwoButtonNotification(String location) {
        Log.d("DEBUG", "Invoking TwoButtonNotification with location: " + location);
        Intent notificationIntent = new Intent(this, TwoButtonNotificationActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notificationIntent.putExtra("location", location);

        startActivity(notificationIntent);
    }

}