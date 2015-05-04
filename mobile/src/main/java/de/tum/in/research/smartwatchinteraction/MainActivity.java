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

        int notificationId = 1;

        CharSequence title = getString(getResources().getIdentifier(locationName, "string", getPackageName()));

        // Build Main Intent
        Intent openIntent = new Intent(this, MainActivity.class);
        openIntent.putExtra("notification_id", notificationId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, openIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Create an intent for the vote_up action
        Intent actionIntent = new Intent(this, MainActivity.class); // Switch to Vote Activity
        actionIntent.putExtra(getString(R.string.close), true);
        PendingIntent actionPendingIntent =
                PendingIntent.getActivity(this, 0, actionIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        // Create the action
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.thumb_up,
                        getString(R.string.vote_up), actionPendingIntent)
                        .build();

        // Build Main Notification
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        // SmallIcon is only for the Notification on the Phone
                        // App Icon for Wear is used on the smartwatch
                        //.setSmallIcon(R.mipmap.ic_launcher)
                        .setSmallIcon(R.drawable.thumb_up)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(), R.drawable.tum))
                        .setContentTitle(title)
                        .setContentText(getString(R.string.notification_text))
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .extend(new NotificationCompat.WearableExtender().addAction(action));

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());


    }

}
