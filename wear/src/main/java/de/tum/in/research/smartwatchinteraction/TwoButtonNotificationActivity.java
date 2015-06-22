package de.tum.in.research.smartwatchinteraction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.wearable.activity.ConfirmationActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class TwoButtonNotificationActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_button_notification);

        // Switch on screen
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent i = getIntent();
        String location = i.getExtras().getString("location");

        Log.d("DEBUG", "Location in intent:" + location);

        // Get text and image from the identifier
        String text = getResources().getString(getResources().getIdentifier(location, "string", getPackageName()));
        Drawable background = getLocationImage(text);

        View view = findViewById(R.id.two_button_background);
        view.setBackground(background);

        TextView textView = (TextView) findViewById(R.id.two_button_text);
        textView.setText(text);
    }

    /**
     * Get the image from the location identifier
     * @param location
     * @return
     */
    private Drawable getLocationImage(String location) {
        if (location.equals(getResources().getString(R.string.lmu_mensa))) {
            return getResources().getDrawable(R.drawable.lmu_mensa, null);
        } else if (location.equals(getResources().getString(R.string.lmu_losteria))) {
            return getResources().getDrawable(R.drawable.lmu_losteria, null);
        } else if (location.equals(getResources().getString(R.string.lmu_tijuana))) {
            return getResources().getDrawable(R.drawable.lmu_tijuana, null);
        }
        return null;
    }


    public void voteUp(View view) {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        startActivity(intent);
        this.finish();
    }

    public void voteDown(View view) {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        startActivity(intent);
        this.finish();
    }

}
