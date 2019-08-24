package net.hmzs.tools.utils;

import android.annotation.SuppressLint;
import android.os.Build;

/**
 * 系统OS相关工具
 *
 * @author Joe
 * @version 1.0 2018/05/11
 */
public class OSUtils {
    /**
     * 是否使用沉浸栏
     *
     * @return : true: 使用沉浸栏
     */
    public static boolean hasImmersionBar() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * Android 2.2
     * @return
     */
    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    /**
     * Android 3.0
     * @return
     */
    public static boolean hasHoneyComb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * Android 4.0.1
     * @return
     */
    public static boolean hasIceCreamSandwich() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return true;
        }
        return false;
    }

    /**
     * Android 4.0.3
     * @return
     */
    public static boolean hasSandwichMr1() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            return true;
        }
        return false;
    }

    /**
     * Android 4.1
     * @return
     */
    public static boolean hasJellyBean() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.JELLY_BEAN) {
            return true;
        }
        return false;
    }

    /**
     * Android 4.4
     * @return
     */
    public static boolean hasKitkat() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return true;
        }
        return false;
    }

    /**
     * Android 6.0
     * @return
     */
    public static boolean hasMarshmallow() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return true;
        }
        return false;
    }

    /**
     * Android 7.0
     * @return
     */
    public static boolean hasNougat() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return true;
        }
        return false;
    }

	/**
	 * Android 8.0
	 * @return
	 */
//	@SuppressLint("ObsoleteSdkInt")
//    public static boolean hasO() {
//		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//			return true;
//		}
//		return false;
//	}
}
