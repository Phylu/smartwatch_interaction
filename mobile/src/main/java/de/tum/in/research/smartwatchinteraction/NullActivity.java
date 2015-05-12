package de.tum.in.research.smartwatchinteraction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class NullActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.cancelNotification();
        this.finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        this.cancelNotification();
        this.finish();
    }

    public void cancelNotification() {
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.cancelAll();
    }

}