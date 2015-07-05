package de.tum.in.research.smartwatchinteraction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.DismissOverlayView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class SwipeNotificationActivity extends Activity {

    private DismissOverlayView mDismissOverlay;
    private GestureDetector mDetector;
    private SwipeView mSwipeView;
    private BoxInsetLayout mViewLayout;
    private String DEBUG_TAG = "DEBUG: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_notification);
        System.out.println("Started Swipe Notification Activity");

        // Obtain the DismissOverlayView element
        mDismissOverlay = (DismissOverlayView) findViewById(R.id.dismiss_overlay);

        // Get the main Layout to draw the overlay upon
        mViewLayout = (BoxInsetLayout) findViewById(R.id.swipe_view);
        mSwipeView = new SwipeView(this);

        // Configure a gesture detector
        mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {


            @Override
            public void onLongPress(MotionEvent ev) {
                mDismissOverlay.show();
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                    float distanceY) {
                // Check direction to draw correct color
                if (e1.getX() - e2.getX() > 0) {
                    createOverlay(getResources().getColor(R.color.green), e2.getX());
                } else {
                    createOverlay(getResources().getColor(R.color.red), e2.getX());
                }

                return true;
            }

            @Override
            public boolean onFling(MotionEvent event1, MotionEvent event2,
                                   float velocityX, float velocityY) {

                // Positive velocity is fling from left to right
                if (velocityX > 250) {
                    voteDown();
                } else if (velocityX < -250) {
                    voteUp();
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
        }
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

    private void createOverlay(int color, float x) {
        Log.d("DEBUG", "Creating overlay");
//        View view = findViewById(R.id.swipe_overlay);
//        view.setBackgroundColor(color);
//        view.setAlpha(0.3f);

        mSwipeView.update(color, (int) x);
        mViewLayout.removeView(mSwipeView);
        mViewLayout.addView(mSwipeView);

    }

    private void removeOverlay() {
        Log.d("DEBUG", "Removing overlay");
        mViewLayout.removeView(mSwipeView);
//        View view = findViewById(R.id.swipe_overlay);
//        view.setAlpha(0.0f);
    }

    public class SwipeView extends View {
        private ShapeDrawable mDrawable;

        public SwipeView(Context context) {
            super(context);
            setAlpha(0.5f);
        }

         public void update(int color, int x) {

             int y = 0;
             int width = 320;
             int height = 320;

             mDrawable = new ShapeDrawable(new OvalShape());
             mDrawable.getPaint().setColor(color);
             mDrawable.setBounds(x, y, x + width, y + height);

             invalidate();
        }

        protected void onDraw(Canvas canvas) {
            mDrawable.draw(canvas);
        }
    }

}
