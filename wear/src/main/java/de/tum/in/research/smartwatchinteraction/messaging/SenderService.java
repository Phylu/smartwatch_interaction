package de.tum.in.research.smartwatchinteraction.messaging;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.wearable.activity.ConfirmationActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by janosch on 07.07.15.
 */
public class SenderService extends IntentService implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    // Messaging Service
    protected GoogleApiClient mGoogleApiClient;

    public SenderService() {
        super("SenderService");
    }

    @Override
    public void onCreate() {
        super.onCreate();


        Log.d("SenderService", "Created");

        // Looper to make sure, that this service keeps alive until message is send to phone
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        // Create wearable connection
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    /**
     * Handle intent that should send msg to phone
     */
    protected void onHandleIntent(Intent intent) {
        mGoogleApiClient.blockingConnect();

        Log.d("SenderService", "Handling Intent");
        Bundle extras = intent.getExtras();
        if (extras != null) {
        String mClass = extras.getString("class");
            int vote = Integer.valueOf(extras.getString("vote"));
            if (vote == 1) {
                voteUp(mClass);
            } else if (vote == -1) {
                voteDown(mClass);
            }
            Looper.loop();
        }
    }

    /**
     * Vote something up
     *
     * @param mClass
     */
    public void voteUp(String mClass) {
        if (mGoogleApiClient.isConnected()) {
            MessageThread t = new MessageThread(mGoogleApiClient, mClass, "1");
            t.start();
        } else {
            Toast.makeText(this, "Phone not connected", Toast.LENGTH_LONG).show();
        }
        // TODO: Get voting information and send msg to phone
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        startActivity(intent);
    }

    /**
     * Vote something down
     *
     * @param mClass
     */
    public void voteDown(String mClass) {
        if (mGoogleApiClient.isConnected()) {
            MessageThread t = new MessageThread(mGoogleApiClient, mClass, "-1");
            t.start();
        } else {
            Toast.makeText(this, "Phone not connected", Toast.LENGTH_LONG).show();
        }
        // TODO: Get voting information and send msg to phone
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        startActivity(intent);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("SenderService", "Connected to Phone");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}
