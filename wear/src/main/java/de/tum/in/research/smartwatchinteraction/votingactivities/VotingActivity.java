package de.tum.in.research.smartwatchinteraction.votingactivities;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import de.tum.in.research.smartwatchinteraction.R;
import de.tum.in.research.smartwatchinteraction.messaging.ListenerService;
import de.tum.in.research.smartwatchinteraction.messaging.MessageThread;
import de.tum.in.research.smartwatchinteraction.messaging.SenderService;

/**
 * Created by janosch on 06.07.15.
 * Abstract class to provide some voting functionality
 */
public abstract class VotingActivity extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Switch on the watch screen to show activity
     */
    protected void switchOnScreen() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * Vote something up
     * @param view
     */
    public void voteUp(View view) {
        Intent intent = new Intent(this, SenderService.class);
        intent.putExtra("class", getClassIdentifier());
        intent.putExtra("vote", "1");
        startService(intent);
        this.finish();
    }

    /**
     * Vote something down
     * @param view
     */
    public void voteDown(View view) {
        Intent intent = new Intent(this, SenderService.class);
        intent.putExtra("class", getClassIdentifier());
        intent.putExtra("vote", "-1");
        startService(intent);
        this.finish();
    }

    /**
     * Return a class identifier to be send via msg
     * @return
     */
    private String getClassIdentifier() {
        if (this.getClass() == ActionButtonNotificationActivity.class) {
            return getResources().getString(R.string.action_button_notification);
        } else if (this.getClass() == TwoButtonNotificationActivity.class) {
            return getResources().getString(R.string.two_button_notification);
        } else if (this.getClass() == SwipeNotificationActivity.class) {
            return getResources().getString(R.string.swipe_notification);
        }
        return "";
    }

}
