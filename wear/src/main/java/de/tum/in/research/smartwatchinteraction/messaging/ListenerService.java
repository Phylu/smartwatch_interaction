package de.tum.in.research.smartwatchinteraction.messaging;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import de.tum.in.research.smartwatchinteraction.R;
import de.tum.in.research.smartwatchinteraction.votingactivities.ActionButtonNotificationActivity;
import de.tum.in.research.smartwatchinteraction.votingactivities.SwipeNotificationActivity;
import de.tum.in.research.smartwatchinteraction.votingactivities.TwoButtonNotificationActivity;
import de.tum.in.research.smartwatchinteraction.votingactivities.VotingHelper;

/**
 * Created by janosch on 22.06.15.
 */
public class ListenerService extends WearableListenerService  {

    private int NOTIFICATION_ID = 1;

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        String msg = new String(messageEvent.getData());
        Log.d("ListenerService", "onMessageReceived(): " + messageEvent.getPath() + "/" + msg);
        if(messageEvent.getPath().equals(getResources().getString(R.string.swipe_notification))) {
            vibrate();
            startSwipeNotification(msg);
        } else if (messageEvent.getPath().equals(getResources().getString(R.string.two_button_notification))) {
            vibrate();
            startTwoButtonNotification(msg);
        } else if (messageEvent.getPath().equals(getResources().getString(R.string.action_button_notification))) {
            startActionButtonNotification(msg);
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

        String title = VotingHelper.getLocationName(this, location);
        Bitmap backgroundImage = VotingHelper.getLocationBitmap(this, title);

        // Create an intent for the vote_up action
        Intent actionVoteUpIntent = new Intent(this, ActionButtonNotificationActivity.class); // Switch to Vote Activity
        actionVoteUpIntent.putExtra("vote", "1");
        PendingIntent actionVoteUpPendingIntent =
                PendingIntent.getActivity(this, 0, actionVoteUpIntent, 0);

        // Create an intent for the vote_up action
        Intent actionVoteDownIntent = new Intent(this, ActionButtonNotificationActivity.class); // Switch to Vote Activity
        actionVoteUpIntent.putExtra("vote", "1");
        PendingIntent actionVoteDownPendingIntent =
                PendingIntent.getActivity(this, 0, actionVoteDownIntent, 0);

        // Create the action
        NotificationCompat.Action actionVoteUp =
                new NotificationCompat.Action.Builder(R.drawable.thumb_up_white_300,
                        getString(R.string.vote_up), actionVoteUpPendingIntent)
                        .build();
        NotificationCompat.Action actionVoteDown =
                new NotificationCompat.Action.Builder(R.drawable.thumb_down_white_300,
                        getString(R.string.vote_down), actionVoteDownPendingIntent)
                        .build();

        // Build Main Notification
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(getString(R.string.notification_text))
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .extend(new NotificationCompat.WearableExtender()
                                        .addAction(actionVoteUp)
                                        .addAction(actionVoteDown)
                                        .setBackground(backgroundImage)
                        );

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());


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