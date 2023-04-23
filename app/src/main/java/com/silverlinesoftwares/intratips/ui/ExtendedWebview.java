package com.silverlinesoftwares.intratips.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ExtendedWebview extends WebView {
    public ExtendedWebview(@NonNull Context context) {
        super(context);
    }

    public ExtendedWebview(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent p_event)
    {
        return true;
    }



}
