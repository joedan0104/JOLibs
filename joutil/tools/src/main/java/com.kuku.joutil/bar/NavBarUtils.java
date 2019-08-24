package net.hmzs.tools.bar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import net.hmzs.tools.utils.ContextHolder;

/**
 * Author zhaolei
 * Date 2018/2/27
 * Description desc
 */

public class NavBarUtils {

    /**
     * 获取导航栏高度
     *
     * @return 导航栏高度
     */
    public static int getNavBarHeight() {
        Resources res = ContextHolder.getResource();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId != 0) {
            return res.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    /**
     * 设置导航栏是否可见
     *
     * @param activity  activity
     * @param isVisible {@code true}: 可见<br>{@code false}: 不可见
     */
    public static void setNavBarVisibility(final Activity activity, boolean isVisible) {
        if (isVisible) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            View decorView = activity.getWindow().getDecorView();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                int visibility = decorView.getSystemUiVisibility();
                decorView.setSystemUiVisibility(visibility & ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }

    /**
     * 设置导航栏沉浸式
     *
     * @param activity activity
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setNavBarImmersive(final Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 判断导航栏是否可见
     *
     * @param activity activity
     * @return {@code true}: 可见<br>{@code false}: 不可见
     */
    public static boolean isNavBarVisible(final Activity activity) {
        boolean isNoLimits = (activity.getWindow().getAttributes().flags
                & WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) != 0;
        if (isNoLimits) return false;
        View decorView = activity.getWindow().getDecorView();
        int visibility = decorView.getSystemUiVisibility();
        return (visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
    }
}
