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

import java.security.SecureRandom;
import java.util.Random;

import de.tum.in.research.smartwatchinteraction.storage.Participant;

public class MainActivity extends Activity {

    public static final String PREFS_NAME = "SmartwatchInteractionPrefs";
    SharedPreferences settings;
    Participant participant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = getSharedPreferences(PREFS_NAME, 0);
        setContentView(R.layout.activity_main);
    }

    @Override
    /**
     * Clear everything when the app resumes
     */
    protected void onResume() {
        super.onResume();
        reset();
    }

    /**
     * Clear all fields
     */
    private void reset() {
        resetParticipantTextField();
        deactivateButton();
    }

    public void createParticipant(View view) {

        // Create Participant Number
        int vpn_nr = createNextParticipantNumber();
        updateParticipantTextField(vpn_nr);

        // Create Group Number for Participant
        int group_nr = createRandomGroupNumber();

        // Create Storage element
        participant = Participant.getInstance();
        participant.newParticipant(vpn_nr, group_nr);
        Log.d("DEBUG: ", participant.toString());

        // Let the user procceed
        activateButton();
    }

    /**
     * Create the number of the next participant
     * @return
     */
    private int createNextParticipantNumber() {
        int vpn_nr = settings.getInt("vpn_nr", 0);
        vpn_nr++;

        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("vpn_nr", vpn_nr);
        editor.commit();

        return vpn_nr;
    }

    /**
     * Write the vpn_nr on the screen
     * @param vpn_nr
     */
    private void updateParticipantTextField(int vpn_nr) {
        TextView vpn_nr_textfield = (TextView) findViewById(R.id.vpn_nr);
        vpn_nr_textfield.setText(String.format(getString(R.string.note_vpn_nr), vpn_nr));
    }

    /**
     * Reset the Text field containing participant information
     */
    private void resetParticipantTextField() {
        TextView vpn_nr_textfield = (TextView) findViewById(R.id.vpn_nr);
        vpn_nr_textfield.setText("");
    }

    /**
     * Create a random participant number between 1 and 6
     * @return
     */
    private int createRandomGroupNumber() {
        int min = 1;
        int max = 6;

        Random r = new SecureRandom();
        return r.nextInt(max - min + 1) + min;
    }

    /**
     * Activate the forward button
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
     * Deactivate the forward button
     */
    private void deactivateButton() {
        ImageButton forwardButton = (ImageButton) findViewById(R.id.forward_button);
        forwardButton.setBackground(getResources().getDrawable(R.drawable.arrow_right_grey));

        forwardButton.setOnClickListener(null);
    }

    /**
     * Forward to the correct Method page based on the group of the participant
     * @param view
     */
    public void forwardButtonClick(View view) {
        Intent intent = new Intent(this, participant.order[0]);
        startActivity(intent);
        finish();
    }
}
