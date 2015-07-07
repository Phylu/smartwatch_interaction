package de.tum.in.research.smartwatchinteraction.TrialActivities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import de.tum.in.research.smartwatchinteraction.R;
import de.tum.in.research.smartwatchinteraction.StartActivity;
import de.tum.in.research.smartwatchinteraction.storage.Participant;

public abstract class TrialActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String PREFS_NAME = "SmartwatchInteractionPrefs";
    protected  SharedPreferences settings;
    protected Participant participant;
    protected int counter = 0;
    protected GoogleApiClient mGoogleApiClient;
    protected String[] locations;

    TextView headingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the current participant
        participant = Participant.getInstance();

        // Get settings
        settings = getSharedPreferences(PREFS_NAME, 0);

        // Load View
        setContentView(R.layout.activity_trial);

        // Get the View for the heading
        headingTextView = (TextView) findViewById(R.id.heading_notification);

        // Load locations order string
        locations = new String[] {"lmu_mensa", "lmu_losteria", "lmu_tijuana"};

        // Create wearable connection
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void activateForwardButtonIfCounterAboveThree() {
        if (counter >= 3) {
            activateButton();
        }
    }


    /**
     * The forward button gets activated
     */
    private void activateButton() {
        ImageButton forwardButton = (ImageButton) findViewById(R.id.forward_button);
        forwardButton.setBackground(getResources().getDrawable(R.drawable.arrow_right));

        forwardButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                forwardButtonClick(v);
            }
        });
    }

    /**
     * How should a notification be invoked
     * @param view
     */
    public boolean createNotification(View view) {
        if (counter >= 3) {
            Toast.makeText(getApplicationContext(), getString(R.string.error_three_notifications_send), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * What view comes next?
     * @param view
     */
    public void forwardButtonClick(View view) {
        Intent intent;
        for (int i = 0; i < 3; i++) {
            // If this is the last view, go to StartActivity
            if (i == 2) {
                participant.store(getApplicationContext());
                intent = new Intent(this, StartActivity.class);
                startActivity(intent);
                break;
            }
            // Check which TrialActivity we are in and procceed with the next
            if (this.getClass().equals(participant.order[i])) {
                intent = new Intent(this, participant.order[i+1]);
                startActivity(intent);
                break;
            }
        }
        finish();
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
