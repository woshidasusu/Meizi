package com.dasu.ganhuo.ui.base;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by dasu on 2017/4/10.
 */

public class ActivityStack {
    private static Stack<Activity> sActivityStack;
    private static ActivityStack sInstance;

    private ActivityStack() {

    }

    public static ActivityStack getInstance() {
        if (sInstance == null) {
            sInstance = new ActivityStack();
        }
        return sInstance;
    }

    public void pushActivity(Activity activity) {
        if (sActivityStack == null) {
            sActivityStack = new Stack<>();
        }
        sActivityStack.push(activity);
    }

    public void popActivity(Activity activity) {
        if (activity != null) {
            sActivityStack.remove(activity);
            activity = null;
        }
    }

    public void popAndFinishActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
        }
        popActivity(activity);
    }

    public void popAndFinishActivity() {
        if (sActivityStack != null && sActivityStack.size() <= 0) {
            return;
        }
        Activity activity = sActivityStack.lastElement();
        popAndFinishActivity(activity);
    }

    public void popAllActivityExceptOne(Class<?> cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popAndFinishActivity(activity);
        }
    }

    public void clearActivitys() {
        for (int i = sActivityStack.size() - 1; i >= 0; i--) {
            Activity activity = sActivityStack.get(i);
            popAndFinishActivity(activity);
        }
    }

    public int size() {
        if (sActivityStack != null) {
            return sActivityStack.size();
        }
        return 0;
    }

    public Activity currentActivity() {
        return sActivityStack.lastElement();
    }

    public boolean isBottomActivity(Activity activity) {
        if (size() > 0) {
            return sActivityStack.get(0) == activity;
        } else {
            return true;
        }
    }

}
