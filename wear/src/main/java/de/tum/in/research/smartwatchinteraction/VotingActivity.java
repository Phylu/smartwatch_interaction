package de.tum.in.research.smartwatchinteraction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.wearable.activity.ConfirmationActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by janosch on 06.07.15.
 * Abstract class to provide some voting functionality
 */
public abstract class VotingActivity extends Activity {

    /**
     * Switch on the watch screen to show activity
     */
    protected void switchOnScreen() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    }

    /**
     * Get the image from the location identifier
     * @param location
     * @return
     */
    protected Drawable getLocationImage(String location) {
        if (location.equals(getResources().getString(R.string.lmu_mensa))) {
            return getResources().getDrawable(R.drawable.lmu_mensa, null);
        } else if (location.equals(getResources().getString(R.string.lmu_losteria))) {
            return getResources().getDrawable(R.drawable.lmu_losteria, null);
        } else if (location.equals(getResources().getString(R.string.lmu_tijuana))) {
            return getResources().getDrawable(R.drawable.lmu_tijuana, null);
        }
        return null;
    }

    /**
     * Get the name from the location identifier
     * @param location
     * @return
     */
    protected String getLocationName(String location) {
        return getResources().getString(getResources().getIdentifier(location, "string", getPackageName()));
    }

    /**
     * Vote something up
     * @param view
     */
    public void voteUp(View view) {
        // TODO: Get voting information and send msg to phone
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        startActivity(intent);
        this.finish();
    }

    /**
     * Vote something down
     * @param view
     */
    public void voteDown(View view) {
        // TODO: Get voting information and send msg to phone
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        startActivity(intent);
        this.finish();
    }



}
