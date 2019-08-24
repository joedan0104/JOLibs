package net.hmzs.tools.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: zhaolei
 * Date  : 2018/2/26
 * Desc  : 服务相关工具类
 */
public final class ServiceUtils {

    private ServiceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取所有运行的服务
     *
     * @return 服务名集合
     */
    public static Set getAllRunningService() {
        ActivityManager am =
                (ActivityManager) ContextHolder.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return Collections.emptySet();
        List<RunningServiceInfo> info = am.getRunningServices(0x7FFFFFFF);
        Set<String> names = new HashSet<>();
        if (info == null || info.size() == 0) return null;
        for (RunningServiceInfo aInfo : info) {
            names.add(aInfo.service.getClassName());
        }
        return names;
    }

    /**
     * 判断服务是否运行
     *
     * @param className 完整包名的服务类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isServiceRunning(final String className) {
        ActivityManager am =
                (ActivityManager) ContextHolder.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return false;
        List<RunningServiceInfo> info = am.getRunningServices(0x7FFFFFFF);
        if (info == null || info.size() == 0) return false;
        for (RunningServiceInfo aInfo : info) {
            if (className.equals(aInfo.service.getClassName())) return true;
        }
        return false;
    }
}
