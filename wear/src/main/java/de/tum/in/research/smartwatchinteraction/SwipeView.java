package de.tum.in.research.smartwatchinteraction;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;

/**
 * View to be drawn while swiping
 */
public class SwipeView extends View {
    private ShapeDrawable mDrawable;
    private int width = 320;
    private int height = 320;
    private int y = 0;

    /**
     * Create new swipe view
     * @param context
     */
    public SwipeView(Context context) {
        super(context);
        setAlpha(0.5f);
    }

    /**
     * Update the overlay information
     * @param color Red or Green
     * @param x Position on the x axis
     */
     public void update(int color, int x) {
         mDrawable = new ShapeDrawable(new OvalShape());
         mDrawable.getPaint().setColor(color);
         mDrawable.setBounds(x, y, x + width, y + height);

         // Redraw the View
         invalidate();
    }

    /**
     * Draw the Shape on the Canvas
     * @param canvas
     */
    protected void onDraw(Canvas canvas) {
        mDrawable.draw(canvas);
    }
}
