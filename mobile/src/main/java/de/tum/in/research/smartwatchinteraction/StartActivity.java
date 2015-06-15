package de.tum.in.research.smartwatchinteraction;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

import de.tum.in.research.smartwatchinteraction.storage.Participant;

public class StartActivity extends Activity {

    public static final String PREFS_NAME = "SmartwatchInteractionPrefs";
    SharedPreferences settings;
    Participant participant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        settings = getSharedPreferences(PREFS_NAME, 0);
        setContentView(R.layout.activity_start);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reset();
    }

    private void reset() {
        resetParticipantTextField();
        deactivateButton();
    }

    public void createParticipant(View view) {

        int vpn_nr = createNextParticipantNumber();
        updateParticipantTextField(vpn_nr);

        int group_nr = createRandomGroupNumber();

        participant = new Participant(vpn_nr, group_nr);

        activateButton();
    }

    private int createNextParticipantNumber() {
        int vpn_nr = settings.getInt("vpn_nr", 0);
        vpn_nr++;

        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("vpn_nr", vpn_nr);
        editor.commit();

        return vpn_nr;
    }

    private void updateParticipantTextField(int vpn_nr) {
        TextView vpn_nr_textfield = (TextView) findViewById(R.id.vpn_nr);
        vpn_nr_textfield.setText(String.format(getString(R.string.note_vpn_nr), vpn_nr));
    }

    private void resetParticipantTextField() {
        TextView vpn_nr_textfield = (TextView) findViewById(R.id.vpn_nr);
        vpn_nr_textfield.setText("");
    }

    private int createRandomGroupNumber() {
        int min = 1;
        int max = 6;

        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
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

    public void forwardButtonClick(View view) {
        Intent intent = new Intent(this, ActionButtonActivity.class);
        intent.putExtra("participant", participant);
        startActivity(intent);
    }
}
