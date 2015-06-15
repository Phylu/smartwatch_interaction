package de.tum.in.research.smartwatchinteraction.storage;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by janosch on 15.06.15.
 */
public class Participant implements Serializable {

    int vpn_nr;
    int group;
    Trial[] swipe = new Trial[3];
    Trial[] two_button = new Trial[3];
    Trial[] action_button = new Trial[3];

    public Participant(int vpn_nr, int group) {
        this.vpn_nr = vpn_nr;
        this.group = group;
    }

    public void addSwipe(int number, int timer, short vote) {
        swipe[number] = new Trial(timer, vote);
    }

    public void addTwoButton(int number, int timer, short vote) {
        two_button[number] = new Trial(timer, vote);
    }

    public void addActionButton(int number, int timer, short vote) {
        action_button[number] = new Trial(timer, vote);
    }

    public String toString() {
        String result = "vpn_nr: " + vpn_nr + "\n";
        result += "group: " + group + "\n";
        result += "action_buttons: " + action_button[0] + action_button[1] + action_button[2] + "\n";
        result += "two_buttons: " + two_button[0] + two_button[1] + two_button[2] + "\n";
        result += "swipes: " + swipe[0] + swipe[1] + swipe[2] + "\n";
        return  result;
    }

}
