package de.tum.in.research.smartwatchinteraction.messaging;

import android.os.Looper;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by janosch on 22.06.15.
 */
public class MessageThread extends Thread {

    GoogleApiClient mGoogleApiClient;
    String method;
    String voting;

    public MessageThread(GoogleApiClient mGoogleApiClient, String method, String voting) {
            this.mGoogleApiClient = mGoogleApiClient;
            this.method = method;
            this.voting = voting;
        };


    public void run() {
        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
        for (Node node : nodes.getNodes()) {
            Log.d("DEBUG", "Sending to: " + node.getDisplayName());
            Wearable.MessageApi.sendMessage(mGoogleApiClient, node.getId(), method, voting.getBytes()).await();
        }
        if (Looper.myLooper() != null) {
            Looper.myLooper().quit();
        }
    }

}