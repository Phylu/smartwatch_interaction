package de.tum.in.research.smartwatchinteraction;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import de.tum.in.research.smartwatchinteraction.storage.Participant;

public class ActionButtonActivity extends TrialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        headingTextView.setText(R.string.action_button_notification);

    }

    @Override
    public void createNotification(View view) {

    }

    @Override
    public void forwardButtonClick(View view) {

    }


}
