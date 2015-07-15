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
    private float firstX;
    private float lastX;
    private short lastDirection;
    private float lastDistance;

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
            /**
             * Show the dismiss overlay on a long press
             */
            public void onLongPress(MotionEvent ev) {
                mDismissOverlay.show();
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                    float distanceY) {
                firstX = e1.getX();
                lastX = e2.getX();
                lastDistance = e1.getX() - e2.getX();
                // If we start on the right side of the screen, it is a vote up
                if (e1.getX() > 200) {
                    createOverlay(greenSwipeView, e2.getX());
                    removeOverlay(redSwipeView);
                    lastDirection = SwipeNotificationActivity.UP;
                // If we start on the left side of the screen, it is a vote down
                } else if (e1.getX() < 120) {
                    // Overlay has to be right of current position, therefore substract screen size
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

    @Override
    /**
     * Capture all touch events
     * Makes it posible to recognize, when a touch event has ended
     */
    public boolean onTouchEvent(MotionEvent ev) {
        // When somebody touches the screen, stop the animation
        cancelAnimations();

        // If the finger is removed
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            removeBothOverlays();
            // Check the direction of the voting, where the finger is currently and if a great enough distance is traveled
            if (lastDirection == SwipeNotificationActivity.UP && firstX > 200 && lastX < 160 && lastDistance > 50) {
                voteUp(mViewLayout);
            } else if (lastDirection == SwipeNotificationActivity.DOWN && firstX < 120 && lastX > 160 && lastDistance < -50) {
                voteDown(mViewLayout);
            }
        }

        // Give the event to our EventDetector
        return mDetector.onTouchEvent(ev) || super.onTouchEvent(ev);
    }

    /**
     * Cancel all animations
     */
    private void cancelAnimations() {
        redSwipeAnimation.cancel();
        greenSwipeAnimation.cancel();
    }

    /**
     * Position the overlay on the screen
     * @param overlay   Overlay to position
     * @param x         X coordinate to show overlay
     */
    private void createOverlay(SwipeView overlay, float x) {
        Log.d("DEBUG", "Creating overlay");
        overlay.setX(x);
        overlay.invalidate();
    }

    /**
     * Move the overlay out of the screen
     * @param overlay   Overlay to remove
     */
    private void removeOverlay(SwipeView overlay) {
        Log.d("DEBUG", "Removing overlay");
        overlay.setX(-320);
    }

    /**
     * Remove both overlays from the srceen
     */
    private void removeBothOverlays() {
        removeOverlay(redSwipeView);
        removeOverlay(greenSwipeView);
    }

}
