package de.tum.in.research.smartwatchinteraction.votingactivities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import de.tum.in.research.smartwatchinteraction.R;

public class ActionButtonNotificationActivity extends VotingActivity {

    private static int UP = 1;
    private static int DOWN = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        int vote = Integer.valueOf(i.getExtras().getString("vote"));

        Log.d("DEBUG", "Voting in intent:" + vote);

        if (vote == ActionButtonNotificationActivity.UP) {
            voteUp(null);
        } else if (vote == ActionButtonNotificationActivity.DOWN) {
            voteDown(null);
        }

        finish();
    }


}
