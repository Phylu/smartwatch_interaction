package de.tum.in.research.smartwatchinteraction.votingactivities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import de.tum.in.research.smartwatchinteraction.R;
import de.tum.in.research.smartwatchinteraction.messaging.MessageThread;

/**
 * Created by janosch on 06.07.15.
 * Abstract class to provide some voting functionality
 */
public abstract class VotingActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    // Messaging Service
    protected GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create wearable connection
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    /**
     * Switch on the watch screen to show activity
     */
    protected void switchOnScreen() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    }

    /**
     * Vote something up
     * @param view
     */
    public void voteUp(View view) {
        if(mGoogleApiClient.isConnected()) {
            MessageThread t = new MessageThread(mGoogleApiClient, getClassIdentifier(), "1");
            t.start();
        } else {
            Toast.makeText(this, "Phone not connected", Toast.LENGTH_LONG).show();
        }
        // TODO: Get voting information and send msg to phone
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        startActivity(intent);
        this.finish();
    }

    /**
     * Vote something down
     * @param view
     */
    public void voteDown(View view) {
        if(mGoogleApiClient.isConnected()) {
            MessageThread t = new MessageThread(mGoogleApiClient, getClassIdentifier(), "-1");
            t.start();
        } else {
            Toast.makeText(this, "Phone not connected", Toast.LENGTH_LONG).show();
        }
        // TODO: Get voting information and send msg to phone
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        startActivity(intent);
        this.finish();
    }

    /**
     * Return a class identifier to be send via msg
     * @return
     */
    private String getClassIdentifier() {
        if (this.getClass() == ActionButtonNotificationActivity.class) {
            return getResources().getString(R.string.action_button_notification);
        } else if (this.getClass() == TwoButtonNotificationActivity.class) {
            return getResources().getString(R.string.two_button_notification);
        } else if (this.getClass() == SwipeNotificationActivity.class) {
            return getResources().getString(R.string.swipe_notification);
        }
        return "";
    }

    // These three methods are needed for the messaging connection to the wear device
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
