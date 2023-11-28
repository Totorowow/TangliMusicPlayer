
package com.tangli.musicplayer.transition;

import android.animation.Animator;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

public class AnimatedVectorDrawableCompat {

    private static final Handler sHandler = new AnimatedVectorDrawableHandler();

    public static void addListener(AnimatedVectorDrawable drawable, Object callback) {
        if (callback instanceof Animatable2.AnimationCallback) {
            drawable.registerAnimationCallback((Animatable2.AnimationCallback) callback);
        } else {
            throw new IllegalArgumentException("Callback parameter must implement " + Animatable2.AnimationCallback.class.getName());
        }
    }

    public static boolean removeListener(AnimatedVectorDrawable drawable, Object callback) {
        if (callback instanceof Animatable2.AnimationCallback) {
            return drawable.unregisterAnimationCallback((Animatable2.AnimationCallback) callback);
        } else {
            throw new IllegalArgumentException("Callback parameter must implement " + Animatable2.AnimationCallback.class.getName());
        }
    }

    public static void removeAllListeners(AnimatedVectorDrawable drawable) {
        drawable.clearAnimationCallbacks();
    }

    public static void start(Animator animator, AnimatedVectorDrawable drawable) {
        Message msg = Message.obtain(sHandler, AnimatedVectorDrawableHandler.MSG_START, new Object[]{animator, drawable});
        sHandler.sendMessageDelayed(msg, animator.getStartDelay());
    }

    public static void stop(Animator animator, AnimatedVectorDrawable drawable) {
        Message msg = Message.obtain(sHandler, AnimatedVectorDrawableHandler.MSG_STOP, new Object[]{animator, drawable});
        sHandler.sendMessage(msg);
    }

    public static void cancel(AnimatedVectorDrawable drawable) {
        drawable.reset();
        sHandler.removeMessages(AnimatedVectorDrawableHandler.MSG_START);
        sHandler.removeMessages(AnimatedVectorDrawableHandler.MSG_STOP);
    }

    private static class AnimatedVectorDrawableHandler extends Handler {

        private static final int MSG_START = 0;
        private static final int MSG_STOP = 1;

        @Override
        public void handleMessage(Message msg) {

            Object[] array = (Object[]) msg.obj;
            Animator animator = (Animator) array[0];
            AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) array[1];

            switch (msg.what) {

                case MSG_START:

                    drawable.start();

                    break;

                case MSG_STOP:

                    drawable.stop();

                    break;
            }
        }
    }
}
