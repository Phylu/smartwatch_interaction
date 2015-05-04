package de.tum.in.research.smartwatchinteraction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Started Main Activity");

        handleCloseIntent();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleCloseIntent();
    }

    private void handleCloseIntent() {
        Intent intent = this.getIntent();
        if (intent.getExtras().getBoolean(getString(R.string.close), false)) {
            cancelNotification();
            this.finish();
        }
    }

    public void cancelNotification() {
        int notificationId = 1;
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        String ns = this.NOTIFICATION_SERVICE;
        notificationManager.cancel(notificationId);
    }

    public void voteUp(View view) {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, getString(R.string.vote_succeeded));
        startActivity(intent);
    }

    public void voteDown(View view) {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, getString(R.string.vote_succeeded));
        startActivity(intent);
    }

}
