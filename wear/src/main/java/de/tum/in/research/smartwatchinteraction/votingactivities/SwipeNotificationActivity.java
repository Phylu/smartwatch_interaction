package de.tum.in.research.smartwatchinteraction.votingactivities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.DismissOverlayView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import de.tum.in.research.smartwatchinteraction.R;
import de.tum.in.research.smartwatchinteraction.viewhelpers.SwipeView;

public class SwipeNotificationActivity extends VotingActivity {

    private static short UP = 1;
    private static short DOWN = 0;

    private DismissOverlayView mDismissOverlay;
    private GestureDetector mDetector;
    private SwipeView mSwipeView;
    private BoxInsetLayout mViewLayout;
    private float lastX;
    private short lastDirection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_notification);
        System.out.println("Started Swipe Notification Activity");

        // Obtain the DismissOverlayView element
        mDismissOverlay = (DismissOverlayView) findViewById(R.id.dismiss_overlay);

        // Get the main Layout to draw the overlay upon
        mViewLayout = (BoxInsetLayout) findViewById(R.id.swipe_background);
        mSwipeView = new SwipeView(this);

        switchOnScreen();

        Intent i = getIntent();
        String location = i.getExtras().getString("location");

        Log.d("DEBUG", "Location in intent:" + location);

        // Get text and image from the identifier
        String text = VotingHelper.getLocationName(this, location);
        Drawable background = VotingHelper.getLocationDrawable(this, text);

        View view = findViewById(R.id.swipe_background);
        view.setBackground(background);

        TextView textView = (TextView) findViewById(R.id.swipe_text);
        textView.setText(text);

        // Configure a gesture detector
        mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {


            @Override
            public void onLongPress(MotionEvent ev) {
                mDismissOverlay.show();
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                    float distanceY) {
                lastX = e2.getX();
                // Check direction to draw correct color
                if (e1.getX() - e2.getX() > 0) {
                    createOverlay(getResources().getColor(R.color.green), e2.getX());
                    lastDirection = SwipeNotificationActivity.UP;
                } else {
                    createOverlay(getResources().getColor(R.color.red), e2.getX() - 320);
                    lastDirection = SwipeNotificationActivity.DOWN;
                }
                return true;
            }
        });

    }

    // Capture long presses to exit the activity
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            removeOverlay();
            if (lastDirection == SwipeNotificationActivity.UP && lastX < 160) {
                voteUp(mSwipeView);
            } else if (lastDirection == SwipeNotificationActivity.DOWN && lastX > 160) {
                voteDown(mSwipeView);
            }
        }
        return mDetector.onTouchEvent(ev) || super.onTouchEvent(ev);
    }

    private void createOverlay(int color, float x) {
        Log.d("DEBUG", "Creating overlay");
        mSwipeView.update(color, (int) x);
        mViewLayout.removeView(mSwipeView);
        mViewLayout.addView(mSwipeView);

    }

    private void removeOverlay() {
        Log.d("DEBUG", "Removing overlay");
        mViewLayout.removeView(mSwipeView);
    }

}
