package de.tum.in.research.smartwatchinteraction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class SwipeNotificationActivity extends Activity {

    private static short UP = 1;
    private static short DOWN = 0;

    private DismissOverlayView mDismissOverlay;
    private GestureDetector mDetector;
    private SwipeView mSwipeView;
    private BoxInsetLayout mViewLayout;
    private String DEBUG_TAG = "DEBUG: ";
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

        // Switch on screen
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        //| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent i = getIntent();
        String location = i.getExtras().getString("location");

        Log.d("DEBUG", "Location in intent:" + location);

        // Get text and image from the identifier
        String text = getResources().getString(getResources().getIdentifier(location, "string", getPackageName()));
        Drawable background = getLocationImage(text);

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

    /**
     * Get the image from the location identifier
     * @param location
     * @return
     */
    private Drawable getLocationImage(String location) {
        if (location.equals(getResources().getString(R.string.lmu_mensa))) {
            return getResources().getDrawable(R.drawable.lmu_mensa, null);
        } else if (location.equals(getResources().getString(R.string.lmu_losteria))) {
            return getResources().getDrawable(R.drawable.lmu_losteria, null);
        } else if (location.equals(getResources().getString(R.string.lmu_tijuana))) {
            return getResources().getDrawable(R.drawable.lmu_tijuana, null);
        }
        return null;
    }

    // Capture long presses to exit the activity
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            removeOverlay();
            if (lastDirection == SwipeNotificationActivity.UP && lastX < 160) {
                voteUp();
            } else if (lastDirection == SwipeNotificationActivity.DOWN && lastX > 160) {
                voteDown();
            }
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
        mSwipeView.update(color, (int) x);
        mViewLayout.removeView(mSwipeView);
        mViewLayout.addView(mSwipeView);

    }

    private void removeOverlay() {
        Log.d("DEBUG", "Removing overlay");
        mViewLayout.removeView(mSwipeView);
    }

    /**
     * View to be drawn while swiping
     */
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
