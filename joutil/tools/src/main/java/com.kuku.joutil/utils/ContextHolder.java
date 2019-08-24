package net.hmzs.tools.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/11/11 16:46
 * <p/>
 * Description: application_context 工具类
 */
public class ContextHolder {
    private static Context APPLICATION_CONTEXT;


    /**
     * 初始化context，如果由于不同机型导致反射获取context失败可以在Application调用此方法
     */
    public static void init(Context context) {
        APPLICATION_CONTEXT = context;
    }

    /**
     * 反射获取 application context
     */
    public static Context getContext() {
        if (null == APPLICATION_CONTEXT) {
            try {
                Application application = (Application) Class.forName("android.app.ActivityThread")
                        .getMethod("currentApplication")
                        .invoke(null, (Object[]) null);
                if (application != null) {
                    APPLICATION_CONTEXT = application;
                    return application;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Application application = (Application) Class.forName("android.app.AppGlobals")
                        .getMethod("getInitialApplication")
                        .invoke(null, (Object[]) null);
                if (application != null) {
                    APPLICATION_CONTEXT = application;
                    return application;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            throw new IllegalStateException("ContextHolder is not initialed, it is recommend to init with application context.");
        }
        return APPLICATION_CONTEXT;
    }

    public static Resources getResource() {
        return getContext().getResources();
    }

    public static String getString(@StringRes int id) {
        return getResource().getString(id);
    }

    public static String getString(@StringRes int id, Object... formatArgs) {
        return getResource().getString(id, formatArgs);
    }

    public static int getColor(@ColorRes int id) {
        return getResource().getColor(id);
    }

    public static Drawable getDrawable(@DrawableRes int id) {
        return getResource().getDrawable(id);
    }

    public static int getDimen(@DimenRes int id) {
        return getResource().getDimensionPixelSize(id);
    }
}
