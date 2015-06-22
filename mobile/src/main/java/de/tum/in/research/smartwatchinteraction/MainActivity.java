package de.tum.in.research.smartwatchinteraction;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.Objects;

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    int NOTIFICATION_ID = 1;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }


    public void createNotification(View view) {
        System.out.println("createNotification Invoked!");
        System.out.println("Tag gotten: " + view.getTag());

        String tag = String.valueOf(view.getTag());
        String[] tagParts = tag.split(":");

        if (tagParts[0].equals("standard")) {
            createStandardNotification(tagParts[1]);
        }
        if (tagParts[0].equals("two_button")) {
            createTwoButtonNotification(tagParts[1]);
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
        if(mGoogleApiClient.isConnected()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
                    for(Node node : nodes.getNodes()) {
                        Log.d("test", "Node set to: " + node.getDisplayName());
                        MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(mGoogleApiClient, node.getId(), "Lunch Checker Service", "Hello World".getBytes()).await();
                        if(!result.getStatus().isSuccess()){
                            Log.e("test", "error");
                        } else {
                            Log.i("test", "success!! sent to: " + node.getDisplayName());
                        }
                    }
                }
            }).start();

        } else {
            Log.e("test", "not connected");
        }
    }

    public void createSwipeNotification(String locationName) {
        // TODO: send message to wear device
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("test", "onConnected");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("test", "Failed to connect to Google API Client");
    }
}
