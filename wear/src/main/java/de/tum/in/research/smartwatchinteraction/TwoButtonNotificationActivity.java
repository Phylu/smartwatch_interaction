package de.tum.in.research.smartwatchinteraction;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class TwoButtonNotificationActivity extends VotingActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_button_notification);
        switchOnScreen();


        Intent i = getIntent();
        String location = i.getExtras().getString("location");

        Log.d("DEBUG", "Location in intent:" + location);

        // Get text and image from the identifier
        String text = getLocationName(location);
        Drawable background = getLocationImage(text);

        View view = findViewById(R.id.two_button_background);
        view.setBackground(background);

        TextView textView = (TextView) findViewById(R.id.two_button_text);
        textView.setText(text);
    }


}
