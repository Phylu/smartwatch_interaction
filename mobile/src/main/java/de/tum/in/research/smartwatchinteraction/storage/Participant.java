package de.tum.in.research.smartwatchinteraction.storage;

import java.io.Serializable;

import de.tum.in.research.smartwatchinteraction.TrialActivities.SwipeActivity;
import de.tum.in.research.smartwatchinteraction.TrialActivities.TrialActivity;
import de.tum.in.research.smartwatchinteraction.TrialActivities.TwoButtonActivity;
import de.tum.in.research.smartwatchinteraction.TrialActivities.ActionButtonActivity;

/**
 * Created by janosch on 15.06.15.
 */
public class Participant implements Serializable {

    int vpn_nr;
    int group;
    public Class<TrialActivity>[] order;
    Trial[] swipe = new Trial[3];
    Trial[] two_button = new Trial[3];
    Trial[] action_button = new Trial[3];

    /**
     * Create new participant object
     * @param vpn_nr    Participant number
     * @param group     Group of participant
     */
    public Participant(int vpn_nr, int group) {
        this.vpn_nr = vpn_nr;
        this.group = group;
        setOrder(group);
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
     */
    public void addSwipe(int number, int timer, short vote) {
        swipe[number] = new Trial(timer, vote);
    }

    /**
     * Add a new TwoButton Voting element
     * @param number
     * @param timer
     * @param vote
     */
    public void addTwoButton(int number, int timer, short vote) {
        two_button[number] = new Trial(timer, vote);
    }

    /**
     * Add a new AcitonButton Voting element
     * @param number
     * @param timer
     * @param vote
     */
    public void addActionButton(int number, int timer, short vote) {
        action_button[number] = new Trial(timer, vote);
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


}
