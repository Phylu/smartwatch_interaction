package de.tum.in.research.smartwatchinteraction.storage;

/**
 * Created by janosch on 15.06.15.
 * Store the information of one trial
 */
public class Trial {

    long timer;
    int vote;

    /**
     * Create new trial
     * @param timer
     * @param vote
     */
    public Trial(long timer, int vote) {
        this.timer = timer;
        this.vote = vote;
    }

    /**
     * Get Timer value
     * @return
     */
    public long getTimer() {
        return this.timer;
    }

    /**
     * Get vote value
     * @return 1 if positive, -1 if negative
     */
    public int getVote() {
        return this.vote;
    }

    public String toString() {
        return String.format("Timer:%d/Vote:%d", timer, vote);
    }

}
