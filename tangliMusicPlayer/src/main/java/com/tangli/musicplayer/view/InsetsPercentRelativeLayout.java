
package com.tangli.musicplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InsetsPercentRelativeLayout extends RelativeLayout {

    public InsetsPercentRelativeLayout(Context context) {
        this(context, null, 0);
    }

    public InsetsPercentRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InsetsPercentRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ViewCompat.setOnApplyWindowInsetsListener(this, new androidx.core.view.OnApplyWindowInsetsListener() {
            @NonNull
            @Override
            public WindowInsetsCompat onApplyWindowInsets(@NonNull View v, @NonNull WindowInsetsCompat insets) {
                setWindowInsets(insets);
                return insets.consumeSystemWindowInsets();
            }
        });

    }

    private void setWindowInsets(WindowInsetsCompat insets) {
        // Now dispatch them to our children
        for (int i = 0, z = getChildCount(); i < z; i++) {
            final View child = getChildAt(i);
            insets = ViewCompat.dispatchApplyWindowInsets(child, insets);
            if (insets.isConsumed()) {
                break;
            }
        }
    }

}
