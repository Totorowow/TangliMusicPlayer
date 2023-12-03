package com.tangli.musicplayer.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.util.Property;
import android.view.ViewGroup;

import com.tangli.musicplayer.R;
import com.tangli.musicplayer.view.MusicCoverView;

import java.util.ArrayList;
import java.util.List;

import static com.tangli.musicplayer.view.MusicCoverView.ALPHA_OPAQUE;
import static com.tangli.musicplayer.view.MusicCoverView.ALPHA_TRANSPARENT;

public class MusicCoverViewTransition extends Transition {

    private static final String PROPNAME_RADIUS = MusicCoverViewTransition.class.getName() + ":radius";
    private static final String PROPNAME_ALPHA = MusicCoverViewTransition.class.getName() + ":alpha";
    private static final String[] sTransitionProperties = {PROPNAME_RADIUS, PROPNAME_ALPHA};

    private static final Property<MusicCoverView, Float> RADIUS_PROPERTY =
            new Property<MusicCoverView, Float>(Float.class, "radius") {
                @Override
                public void set(MusicCoverView view, Float radius) {
                    view.setTransitionRadius(radius);
                }

                @Override
                public Float get(MusicCoverView view) {
                    return view.getTransitionRadius();
                }
            };

    private static final Property<MusicCoverView, Integer> ALPHA_PROPERTY =
            new Property<MusicCoverView, Integer>(Integer.class, "alpha") {
                @Override
                public void set(MusicCoverView view, Integer alpha) {
                    view.setTransitionAlpha(alpha);
                }

                @Override
                public Integer get(MusicCoverView view) {
                    return view.getViewTransitionAlpha();
                }
            };

    private final int mStartShape;

    public MusicCoverViewTransition(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MusicCoverView);
        int shape = a.getInt(R.styleable.MusicCoverView_shape, MusicCoverView.SHAPE_RECTANGLE);
        a.recycle();
        mStartShape = shape;
    }

    public MusicCoverViewTransition(int shape) {
        mStartShape = shape;
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        // Add fake value to force calling of createAnimator method
        captureValues(transitionValues, "start");
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        // Add fake value to force calling of createAnimator method
        captureValues(transitionValues, "end");
    }

    private void captureValues(TransitionValues transitionValues, Object value) {
        if (transitionValues.view instanceof MusicCoverView) {
            transitionValues.values.put(PROPNAME_RADIUS, value);
            transitionValues.values.put(PROPNAME_ALPHA, value);
        }
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {

        if (endValues == null || !(endValues.view instanceof MusicCoverView)) {
            return null;
        }

        MusicCoverView coverView = (MusicCoverView) endValues.view;
        final float minRadius = coverView.getMinRadius();
        final float maxRadius = coverView.getMaxRadius();

        float startRadius, endRadius;
        int startTrackAlpha, endTrackAlpha;

        if (mStartShape == MusicCoverView.SHAPE_RECTANGLE) {
            startRadius = maxRadius;
            endRadius = minRadius;
            startTrackAlpha = ALPHA_TRANSPARENT;
            endTrackAlpha = ALPHA_OPAQUE;
        } else {
            startRadius = minRadius;
            endRadius = maxRadius;
            startTrackAlpha =ALPHA_OPAQUE;
            endTrackAlpha = ALPHA_TRANSPARENT;
        }

        List<Animator> animatorList = new ArrayList<>();

        coverView.setTransitionRadius(startRadius);
        animatorList.add(ObjectAnimator.ofFloat(coverView, RADIUS_PROPERTY, startRadius, endRadius));

        coverView.setTransitionAlpha(startTrackAlpha);
        animatorList.add(ObjectAnimator.ofInt(coverView, ALPHA_PROPERTY, startTrackAlpha, endTrackAlpha));

        AnimatorSet animator = new AnimatorSet();
        animator.playTogether(animatorList);
        return animator;
    }

}
