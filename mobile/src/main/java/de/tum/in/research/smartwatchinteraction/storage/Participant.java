package de.tum.in.research.smartwatchinteraction.storage;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import de.tum.in.research.smartwatchinteraction.R;
import de.tum.in.research.smartwatchinteraction.TrialActivities.ActionButtonActivity;
import de.tum.in.research.smartwatchinteraction.TrialActivities.SwipeActivity;
import de.tum.in.research.smartwatchinteraction.TrialActivities.TrialActivity;
import de.tum.in.research.smartwatchinteraction.TrialActivities.TwoButtonActivity;

/**
 * Created by janosch on 15.06.15.
 */
public class Participant {

    private static Participant singleton;

    int vpn_nr;
    int group;
    public Class<TrialActivity>[] order;
    Trial[] swipe;
    Trial[] two_button;
    Trial[] action_button;
    long counterStart;

    private Participant() {
    }

    /**
     * Get singleton instance
     * @return participant
     */
    public static Participant getInstance() {
        if (singleton == null) {
            singleton = new Participant();
        }
        return singleton;
    }

    /**
     * Create new participant object
     * @param vpn_nr    Participant number
     * @param group     Group of participant
     */
    public void newParticipant(int vpn_nr, int group) {
        this.vpn_nr = vpn_nr;
        this.group = group;
        setOrder(group);

        // Reset information
        swipe = new Trial[3];
        two_button = new Trial[3];
        action_button = new Trial[3];
    }

    /**
     * Set the order in which the Trials should be done
     * @param group
     */
    private void setOrder(int group) {
        if(group == 1) {
            order = new Class[] {ActionButtonActivity.class, TwoButtonActivity.class, SwipeActivity.class};
        } else if (group == 2) {
            order = new Class[] {ActionButtonActivity.class, SwipeActivity.class, TwoButtonActivity.class};
        } else if (group == 3) {
            order = new Class[] {SwipeActivity.class, ActionButtonActivity.class, TwoButtonActivity.class};
        } else if (group == 4) {
            order = new Class[] {SwipeActivity.class, TwoButtonActivity.class, ActionButtonActivity.class};
        } else if (group == 5) {
            order = new Class[] {TwoButtonActivity.class, ActionButtonActivity.class, SwipeActivity.class};
        } else if (group == 6) {
            order = new Class[] {TwoButtonActivity.class, SwipeActivity.class, ActionButtonActivity.class};
        }
    }

    /**
     * Add a new Swipe Voting element
     * @param vote
     */
    public void addSwipe(int vote) {
        for (int i = 0; i < 3; i++) {
            if (swipe[i] == null) {
                swipe[i] = new Trial(getCounter(), vote);
                Log.d("DEBUG: ", "Creating new Swipe Trial: " + swipe[i]);
                break;
            }
        }
    }

    /**
     * Add a new TwoButton Voting element
     * @param vote
     */
    public void addTwoButton(int vote) {
        for (int i = 0; i < 3; i++) {
            if (two_button[i] == null) {
                two_button[i] = new Trial(getCounter(), vote);
                Log.d("DEBUG: ", "Creating new Two Button Trial: " + two_button[i]);
                break;
            }
        }
    }

    /**
     * Add a new AcitonButton Voting element
     * @param vote
     */
    public void addActionButton(int vote) {
        for (int i = 0; i < 3; i++) {
            if (action_button[i] == null) {
                action_button[i] = new Trial(getCounter(), vote);
                break;
            }
        }
    }

    /**
     * Create readable information of object
     * @return
     */
    public String toString() {
        String result = "vpn_nr: " + vpn_nr + "\n";
        result += "group: " + group + "\n";
        result += "action_buttons: " + action_button[0] + ", " + action_button[1] + ", " + action_button[2] + "\n";
        result += "two_buttons: " + two_button[0] + ", " + two_button[1] + ", " + two_button[2] + "\n";
        result += "swipes: " + swipe[0] + ", " + swipe[1] + ", " + swipe[2] + "\n";
        return  result;
    }

    /**
     * Set the counter start time
     */
    public void startCounter() {
        counterStart = new Date().getTime();
    }

    /**
     * Return the difference between the counter start time and now
     * @return  Counter time in Miliseconds
     */
    private long getCounter() {
        long now = new Date().getTime();
        return (now - counterStart);
    }

    /**
     * Store the participant object on disk
     */
    public void store(Context context) {
        Log.d("DEBUG: ", this.toString());

        File file = new File(context.getExternalFilesDir(null), "smartwatch_interaction_output.csv");
        try {
            FileOutputStream outputStream = new FileOutputStream(file, true);
            writeVpnInformation(outputStream);
            writeActionButtonInformation(outputStream);
            writeTwoButtonInformation(outputStream);
            writeSwipeInformation(outputStream);
            outputStream.write("\n".getBytes());
            outputStream.close();
        } catch (java.io.IOException e) {
            Toast.makeText(context.getApplicationContext(), context.getString(R.string.error_cannot_write_output), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * Write general vpn information
     * @param outputStream
     * @throws IOException
     */
    private void writeVpnInformation(FileOutputStream outputStream) throws IOException {
        outputStream.write(String.valueOf(vpn_nr).getBytes());
        outputStream.write(";".getBytes());
        outputStream.write(String.valueOf(group).getBytes());
        outputStream.write(";".getBytes());
    }

    /**
     * Write information about action button trials
     * @param outputStream
     * @throws IOException
     */
    private void writeActionButtonInformation(FileOutputStream outputStream) throws IOException {
        for (int i = 0; i < 3; i++) {
            if (action_button[i] != null) {
                outputStream.write(String.valueOf(action_button[i].getVote()).getBytes());
            }
            outputStream.write(";".getBytes());
            if (action_button[i] != null) {
                outputStream.write(String.valueOf(action_button[i].getTimer()).getBytes());
            }
            outputStream.write(";".getBytes());
        }
    }

    /**
     * Write information about two button trials
     * @param outputStream
     * @throws IOException
     */
    private void writeTwoButtonInformation(FileOutputStream outputStream) throws IOException {
        for (int i = 0; i < 3; i++) {
            if (two_button[i] != null) {
                outputStream.write(String.valueOf(two_button[i].getVote()).getBytes());
            }
            outputStream.write(";".getBytes());
            if (two_button[i] != null) {
                outputStream.write(String.valueOf(two_button[i].getTimer()).getBytes());
            }
            outputStream.write(";".getBytes());
        }
    }

    /**
     * Write information about swipe trials
     * @param outputStream
     * @throws IOException
     */
    private void writeSwipeInformation(FileOutputStream outputStream) throws IOException {
        for (int i = 0; i < 3; i++) {
            if (swipe[i] != null) {
                outputStream.write(String.valueOf(swipe[i].getVote()).getBytes());
            }
            outputStream.write(";".getBytes());
            if (swipe[i] != null) {
                outputStream.write(String.valueOf(swipe[i].getTimer()).getBytes());
            }
            outputStream.write(";".getBytes());
        }
    }

}
