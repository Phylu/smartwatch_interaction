package de.tum.in.research.smartwatchinteraction.viewhelpers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.wearable.R;
import android.util.Log;
import android.view.View;
/**
 * View to be drawn while swiping
 */
public class SwipeView extends View {
    private ShapeDrawable drawable;
    private int width = 320;
    private int height = 320;
    private int y = 0;
    private int x = 0;

    /**
     * Create new swipe view
     * @param context
     */
    public SwipeView(Context context, int color) {
        super(context);
        setAlpha(0.5f);
        setX(-320);

        Log.d(SwipeView.class.getCanonicalName(), "View Created");

        drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(color);
        drawable.setBounds(x, y, x + width, y + height);

        invalidate();
    }

    /**
     * Draw the Shape on the Canvas
     * @param canvas
     */
    protected void onDraw(Canvas canvas) {
        Log.d(SwipeView.class.getCanonicalName(), "View Drawn");
        drawable.draw(canvas);
    }
}
