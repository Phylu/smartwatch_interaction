package de.tum.in.research.measurementstorage;

import android.content.BroadcastReceiver;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by janosch on 12.05.15.
 */
public class Storage implements DataApi.DataListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

private static final String STORAGE_KEY = "de.tum.in.research.measurementstorage";

        private GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle connectionHint) {
                        }
                        @Override
                        public void onConnectionSuspended(int cause) {
                        }
                })
                .addOnConnectionFailedListener(new OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult result) {
                        }
                })
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();

        @Override
        public void onDataChanged(DataEventBuffer dataEventBuffer) {

        }

        @Override
        public void onConnected(Bundle bundle) {

        }

        @Override
        public void onConnectionSuspended(int i) {

        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {

        }

        public int addParticipant() {
                int id = -1;
                PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/count");
                putDataMapReq.getDataMap().putInt(STORAGE_KEY, "VPN1");
                PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
                PendingResult<DataApi.DataItemResult> pendingResult =
                        Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
                return id;
        }

        public void addMeasurement(int id) {

        }

        public void getData() {

                DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                updateCount(dataMap.getInt(COUNT_KEY));
        }
}
