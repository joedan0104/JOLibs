package net.hmzs.tools.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import net.hmzs.tools.R;
import net.hmzs.tools.log.Logger;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/8/4 16:39
 * <p>
 * Description: activity管理类
 */
@SuppressWarnings("unused")
public final class ActivityUtils {
    private static final String        TAG   = "ActivityUtils";
    /** 堆栈管理对象 */
    private static final ActivityStack STACK = new ActivityStack();
    /** 当前显示的Activity */
    private static WeakReference<Activity> topActivity;
    /** 额外操作 */
    private static ExtraOperations         operations;

    /**
     * push this activity to stack
     */
    public static void push(Activity activity) {
        Logger.i(TAG, "push = " + activity);
        STACK.pushToStack(activity);
    }

    /**
     * pop top activity from stack
     */
    public static void pop() {
        Activity activity = STACK.popFromStack();
        if (activity != null) {
            activity.finish();
        }
        Logger.i(TAG, "pop = " + activity);
    }

    /**
     * remove this activity from stack, maybe is null
     */
    public static void remove(Activity activity) {
        Logger.i(TAG, "remove = " + activity);
        STACK.removeFromStack(activity);
    }

    /**
     * finish the top activity
     */
    public static void finish() {
        if (null != topActivity && null != topActivity.get()) {
            finish(topActivity.get());
        }
    }

    /**
     * finish the activity
     */
    private static void finish(Activity activity) {
        if (null != activity) {
            activity.finish();
        }
    }

    /**
     * pop activities until this Activity
     */
    @SuppressWarnings("unchecked")
    public static <T extends Activity> T popUntil(final Class<T> clazz) {
        if (clazz != null) {
            while (!STACK.isEmpty()) {
                final Activity activity = STACK.popFromStack();
                if (activity != null) {
                    // if (clazz.isAssignableFrom(activity.getClass())) {
                    if (clazz.getName().equals(activity.getClass().getName())) {
                        return (T) activity;
                    }
                    finish(activity);
                }
            }
        }
        return null;
    }

    public static void setOperations(ExtraOperations operations) {
        ActivityUtils.operations = operations;
    }

    /**
     * 最后一次尝试退出的时间戳
     */
    private static       long lastExitPressedMills  = 0;
    /**
     * 距上次尝试退出允许的最大时间差
     */
    private static final long MAX_DOUBLE_EXIT_MILLS = 800;

    /**
     * 退出APP
     */
    public static void onExit() {
        final long now = System.currentTimeMillis();
        if (now <= lastExitPressedMills + MAX_DOUBLE_EXIT_MILLS) {
            finishAll();
            if (null != operations) {
                operations.onExit();
            }
            System.exit(0);
        } else {
            if (null != peek()) {
                ToastUtils.showShortWarn(R.string.app_exit);
            }
            lastExitPressedMills = now;
        }
    }

    /**
     * 当APP退出的时候，结束所有Activity
     */
    public static void finishAll() {
        Logger.i(TAG, ">>>>>>>>>>>>>>>>>>> Exit <<<<<<<<<<<<<<<<<<<");
        while (!STACK.isEmpty()) {
            final Activity activity = STACK.popFromStack();
            if (activity != null) {
                Logger.i(TAG, activity.toString());
                activity.finish();
                if (null != operations) {
                    operations.onActivityFinish(activity);
                }
            }
        }
        Logger.i(TAG, ">>>>>>>>>>>>>>>>>>> Complete <<<<<<<<<<<<<<<<<<<");
    }

    /**
     * 获取当前显示的activity
     */
    public static Activity peek() {
        if (null != topActivity && null != topActivity.get()) {
            return topActivity.get();
        } else {
            return STACK.peekFromStack();
        }
    }

    public static void setTopActivity(Activity topActivity) {
        ActivityUtils.topActivity = new WeakReference<>(topActivity);
    }

    /**
     * activity堆栈，用以管理APP中的所有activity
     */
    private static class ActivityStack {
        // activity堆对象
        private final Stack<WeakReference<Activity>> activityStack = new Stack<>();

        /**
         * 堆是否为空
         */
        public boolean isEmpty() {
            return activityStack.isEmpty();
        }

        /**
         * 向堆中push此activity
         */
        public void pushToStack(Activity activity) {
            activityStack.push(new WeakReference<>(activity));
        }

        /**
         * 从堆栈中pop出一个activity对象
         */
        public Activity popFromStack() {
            while (!activityStack.isEmpty()) {
                final WeakReference<Activity> weak     = activityStack.pop();
                final Activity                activity = weak.get();
                if (activity != null) {
                    return activity;
                }
            }
            return null;
        }

        /**
         * 从堆栈中查看一个对象，且不会pop
         */
        public Activity peekFromStack() {
            while (!activityStack.isEmpty()) {
                final WeakReference<Activity> weak     = activityStack.peek();
                final Activity                activity = weak.get();
                if (activity != null) {
                    return activity;
                } else {
                    activityStack.pop();
                }
            }
            return null;
        }

        /**
         * 从堆栈中删除指定对象
         */
        public boolean removeFromStack(Activity activity) {
            for (WeakReference<Activity> weak : activityStack) {
                final Activity act = weak.get();
                if (act == activity) {
                    return activityStack.remove(weak);
                }
            }
            return false;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 接口
    ///////////////////////////////////////////////////////////////////////////

    public interface ExtraOperations {
        /** APP退出时需要额外处理的事情，例如广播的反注册，服务的解绑 */
        void onExit();

        /** activity 销毁时需要额外处理的事情，例如finish动画等 */
        void onActivityFinish(Activity activity);
    }

    /**
     * 通过view暴力获取getContext()(Android不支持view.getContext()了)
     *
     * @param view
     *         要获取context的view
     *
     * @return 返回一个activity
     */
    /**
     * 通过 View 获取Activity
     */
    public static Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return (Activity) view.getRootView().getContext();
    }
}
