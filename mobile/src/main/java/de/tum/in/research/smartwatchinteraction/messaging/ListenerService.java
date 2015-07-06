package de.tum.in.research.smartwatchinteraction.messaging;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.provider.Telephony;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import de.tum.in.research.smartwatchinteraction.R;
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
        Log.i("test", "onMessageReceived(): " + msg);
        if(messageEvent.getPath().equals(getResources().getString(R.string.swipe_notification))) {
            participant.addSwipe(1, 1);
        } else if (messageEvent.getPath().equals(getResources().getString(R.string.two_button_notification))) {
            participant.addTwoButton(1, 1);
        } else {
            super.onMessageReceived(messageEvent);
        }
    }



}