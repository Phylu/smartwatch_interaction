package de.tum.in.research.smartwatchinteraction.votingactivities;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.DismissOverlayView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import de.tum.in.research.smartwatchinteraction.R;
import de.tum.in.research.smartwatchinteraction.viewhelpers.SwipeView;

public class SwipeNotificationActivity extends VotingActivity {

    private static short UP = 1;
    private static short DOWN = 0;

    private DismissOverlayView mDismissOverlay;
    private GestureDetector mDetector;
    private SwipeView redSwipeView;
    private SwipeView greenSwipeView;
    private AnimatorSet redSwipeAnimation;
    private AnimatorSet greenSwipeAnimation;
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
        redSwipeView = new SwipeView(this, getResources().getColor(R.color.red));
        greenSwipeView = new SwipeView(this, getResources().getColor(R.color.green));

        // Move Green Overlay to the right of the screen
        greenSwipeView.setX(320);

        // Draw the overlays
        mViewLayout.addView(redSwipeView);
        mViewLayout.addView(greenSwipeView);

        // Animate the overlays
        redSwipeAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.swipe_anim_red);
        redSwipeAnimation.setTarget(redSwipeView);

        // Animate the overlays
        greenSwipeAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.swipe_anim_green);
        greenSwipeAnimation.setTarget(greenSwipeView);

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
                    createOverlay(greenSwipeView, e2.getX());
                    removeOverlay(redSwipeView);
                    lastDirection = SwipeNotificationActivity.UP;
                } else {
                    createOverlay(redSwipeView, e2.getX() - 320);
                    removeOverlay(greenSwipeView);
                    lastDirection = SwipeNotificationActivity.DOWN;
                }
                return true;
            }
        });

        redSwipeAnimation.start();
        greenSwipeAnimation.start();

    }

//    @Override public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//    }

        // Capture long presses to exit the activity
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        cancelAnimations();

        if (ev.getAction() == MotionEvent.ACTION_UP) {
            removeBothOverlays();
            if (lastDirection == SwipeNotificationActivity.UP && lastX < 160) {
                voteUp(mViewLayout);
            } else if (lastDirection == SwipeNotificationActivity.DOWN && lastX > 160) {
                voteDown(mViewLayout);
            }
        }
        return mDetector.onTouchEvent(ev) || super.onTouchEvent(ev);
    }

    private void cancelAnimations() {
        redSwipeAnimation.cancel();
        greenSwipeAnimation.cancel();
    }

    private void createOverlay(SwipeView overlay, float x) {
        Log.d("DEBUG", "Creating overlay");
        overlay.setX(x);
        overlay.invalidate();
    }

    private void removeOverlay(SwipeView overlay) {
        Log.d("DEBUG", "Removing overlay");
        overlay.setX(-320);
    }

    private void removeBothOverlays() {
        removeOverlay(redSwipeView);
        removeOverlay(greenSwipeView);
    }

}
