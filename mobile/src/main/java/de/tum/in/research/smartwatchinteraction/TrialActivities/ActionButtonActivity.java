package de.tum.in.research.smartwatchinteraction.TrialActivities;

import android.os.Bundle;
import android.view.View;

import de.tum.in.research.smartwatchinteraction.R;

public class ActionButtonActivity extends TrialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        headingTextView.setText(R.string.action_button_notification);

    }

    @Override
    public boolean createNotification(View view) {
        // Do not send more notification
        if (!super.createNotification(view)) {
            return false;
        }
        // TODO: Send notification
        counter++;
        activateForwardButtonIfCounterAboveThree();
        return true;
    }


}
