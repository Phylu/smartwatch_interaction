package de.tum.in.research.smartwatchinteraction.votingactivities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import de.tum.in.research.smartwatchinteraction.R;

public class ActionButtonNotificationActivity extends VotingActivity {

    private static int UP = 1;
    private static int DOWN = -1;
    private int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        int vote = Integer.valueOf(i.getExtras().getString("vote"));

        Log.d("DEBUG", "Voting in intent:" + vote);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        if (vote == ActionButtonNotificationActivity.UP) {
            notificationManager.cancel(NOTIFICATION_ID);
            voteUp(null);
        } else if (vote == ActionButtonNotificationActivity.DOWN) {
            notificationManager.cancel(NOTIFICATION_ID);
            voteDown(null);
        }

        finish();
    }


}
