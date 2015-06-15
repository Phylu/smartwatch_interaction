package de.tum.in.research.smartwatchinteraction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.DismissOverlayView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class SwipeNotificationActivity extends Activity {

    private DismissOverlayView mDismissOverlay;
    private GestureDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_notification);
        System.out.println("Started Swipe Notification Activity");

        // Obtain the DismissOverlayView element
        mDismissOverlay = (DismissOverlayView) findViewById(R.id.dismiss_overlay);

        // Configure a gesture detector
        mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {


            @Override
            public void onLongPress(MotionEvent ev) {
                mDismissOverlay.show();
            }

            @Override
            public boolean onFling(MotionEvent event1, MotionEvent event2,
                                   float velocityX, float velocityY) {

                // Positive velocity is fling from left to right
                if (velocityX > 500) {
                    voteDown();
                } else if (velocityX < -500) {
                    voteUp();
                }

                return true;
            }
        });

    }

    // Capture long presses to exit the activity
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mDetector.onTouchEvent(ev) || super.onTouchEvent(ev);
    }


    public void voteUp() {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        startActivity(intent);
        this.finish();
    }

    public void voteDown() {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        startActivity(intent);
        this.finish();
    }

}
