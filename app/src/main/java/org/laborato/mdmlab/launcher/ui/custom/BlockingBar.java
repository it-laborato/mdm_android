package org.laborato.mdmlab.launcher.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * This view is a transparent bar intended to block user interaction with swipeable system areas (status bar etc.)
 */
public class BlockingBar extends LinearLayout {

    public BlockingBar(Context context ) {
        super( context );
    }

    public BlockingBar(Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
    }

    public BlockingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // Intercepted touch!
        return true;
    }

}
