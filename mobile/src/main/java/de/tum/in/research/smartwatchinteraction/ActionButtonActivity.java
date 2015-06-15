package de.tum.in.research.smartwatchinteraction;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import de.tum.in.research.smartwatchinteraction.storage.Participant;

public class ActionButtonActivity extends TrialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView headingTextView = (TextView) findViewById(R.id.heading_notification);
        headingTextView.setText(R.string.action_button_notification);

    }



}
