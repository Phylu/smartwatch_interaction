package de.tum.in.research.smartwatchinteraction.messaging;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.provider.Telephony;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import de.tum.in.research.smartwatchinteraction.R;
import de.tum.in.research.smartwatchinteraction.TrialActivities.ActionButtonActivity;
import de.tum.in.research.smartwatchinteraction.TrialActivities.SwipeActivity;
import de.tum.in.research.smartwatchinteraction.TrialActivities.TwoButtonActivity;
import de.tum.in.research.smartwatchinteraction.storage.Participant;

/**
 * Created by janosch on 22.06.15.
 */
public class ListenerService extends WearableListenerService {

    Participant participant;

    @Override
    public void onCreate() {
        super.onCreate();
        participant = Participant.getInstance();
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        String msg = new String(messageEvent.getData());
        Log.d("DEBUG", "onMessageReceived(): " + messageEvent.getPath() + "/" + msg);
        if(messageEvent.getPath().equals(getResources().getString(R.string.swipe_notification))) {
            Toast.makeText(getApplicationContext(), getString(R.string.response_received_swipe), Toast.LENGTH_SHORT).show();
            participant.addSwipe(Integer.valueOf(msg));
            sendIntent(SwipeActivity.class);
        } else if (messageEvent.getPath().equals(getResources().getString(R.string.two_button_notification))) {
            Toast.makeText(getApplicationContext(), getString(R.string.response_received_two_button), Toast.LENGTH_SHORT).show();
            participant.addTwoButton(Integer.valueOf(msg));
            sendIntent(TwoButtonActivity.class);
        } else if (messageEvent.getPath().equals(getResources().getString(R.string.action_button_notification))) {
            Toast.makeText(getApplicationContext(), getString(R.string.action_button_notification), Toast.LENGTH_SHORT).show();
            participant.addActionButton(Integer.valueOf(msg));
            sendIntent(ActionButtonActivity.class);
        } else {
            super.onMessageReceived(messageEvent);
        }
    }

    private void sendIntent(Class mClass) {
        Intent intent = new Intent(this, mClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}