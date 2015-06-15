package de.tum.in.research.smartwatchinteraction;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;

import java.util.Objects;

public class MainActivity extends Activity {

    int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createNotification(View view) {
        System.out.println("createNotification Invoked!");
        System.out.println("Tag gotten: " + view.getTag());

        String tag = String.valueOf(view.getTag());
        String[] tagParts = tag.split(":");

        if (tagParts[0].equals("standard")) {
            createStandardNotification(tagParts[1]);
        }

    }

    public void createStandardNotification(String locationName) {

        CharSequence title = getString(getResources().getIdentifier(locationName, "string", getPackageName()));

        // Create an intent for the vote_up action
        Intent actionIntent = new Intent(this, NullActivity.class); // Switch to Vote Activity
        PendingIntent actionPendingIntent =
                PendingIntent.getActivity(this, 0, actionIntent, 0);

        // Create the action
        NotificationCompat.Action actionVoteUp =
                new NotificationCompat.Action.Builder(R.drawable.thumb_up_white,
                        getString(R.string.vote_up), actionPendingIntent)
                        .build();
        NotificationCompat.Action actionVoteDown =
                new NotificationCompat.Action.Builder(R.drawable.thumb_down_white,
                        getString(R.string.vote_down), actionPendingIntent)
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
                                        .setBackground(BitmapFactory.decodeResource(
                                                getResources(), R.drawable.mensa_leopoldstrasse
                                        ))
                        );

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());

    }

    public void createTwoButtonNotification(String locationName) {
        // TODO: send message to wear device
    }

    public void createSwipeNotification(String locationName) {
        // TODO: send message to wear device
    }

}
