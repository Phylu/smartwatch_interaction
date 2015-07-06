package de.tum.in.research.smartwatchinteraction.storage;

/**
 * Created by janosch on 15.06.15.
 */
public class Trial {

    int timer;
    int vote;

    public Trial(int timer, int vote) {
        this.timer = timer;
        this.vote = vote;
    }

    public int getTimer() {
        return this.timer;
    }

    public int getVote() {
        return this.vote;
    }

}
