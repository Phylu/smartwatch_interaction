package de.tum.in.research.smartwatchinteraction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

import de.tum.in.research.smartwatchinteraction.storage.Participant;

public abstract class TrialActivity extends Activity {

    public static final String PREFS_NAME = "SmartwatchInteractionPrefs";
    protected  SharedPreferences settings;
    protected Participant participant;

    TextView headingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        participant = (Participant) intent.getExtras().getSerializable("participant");

        settings = getSharedPreferences(PREFS_NAME, 0);
        setContentView(R.layout.activity_trial);

        headingTextView = (TextView) findViewById(R.id.heading_notification);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void activateButton() {
        ImageButton forwardButton = (ImageButton) findViewById(R.id.forward_button);
        forwardButton.setBackground(getResources().getDrawable(R.drawable.arrow_right));

        forwardButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                forwardButtonClick(v);
            }
        });
    }

    private void deactivateButton() {
        ImageButton forwardButton = (ImageButton) findViewById(R.id.forward_button);
        forwardButton.setBackground(getResources().getDrawable(R.drawable.arrow_right_grey));

        forwardButton.setOnClickListener(null);
    }

    public abstract void createNotification(View view);
    public abstract void forwardButtonClick(View view);

}
