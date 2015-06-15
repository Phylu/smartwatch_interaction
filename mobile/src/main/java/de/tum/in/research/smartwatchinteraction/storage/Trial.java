package de.tum.in.research.smartwatchinteraction.storage;

/**
 * Created by janosch on 15.06.15.
 */
public class Trial {

    int timer;
    short vote;

    public Trial(int timer, short vote) {
        this.timer = timer;
        this.vote = vote;
    }

    public int getTimer() {
        return this.timer;
    }

    public short getVote() {
        return this.vote;
    }

}
