package net.hmzs.tools.utils;

import android.text.TextUtils;

/**
 * 数学算法计算器
 *
 * @author Joe
 * @version 1.0 2018/06/27
 */
public class MathUtils {
    public static double getDoubleFromString(String value) {
        double result = 0;
        try {
            if (!TextUtils.isEmpty(value)) {
                result = Double.parseDouble(value);
            }
        } catch (Exception e) {

        }
        return result;
    }

    public static float getFloatFromString(String value) {
        float result = 0;
        try {
            if (!TextUtils.isEmpty(value)) {
                result = Float.parseFloat(value);
            }
        } catch (Exception e) {

        }
        return result;
    }

    public static int getIntegerFromString(String value) {
        int result = 0;
        try {
            if (!TextUtils.isEmpty(value)) {
                result = Integer.parseInt(value);
            }
        } catch (Exception e) {

        }
        return result;
    }

    public static long getLongFromString(String value) {
        long result = 0;
        try {
            if (!TextUtils.isEmpty(value)) {
                result = Long.getLong(value);
            }
        } catch (Exception e) {

        }
        return result;
    }
}
