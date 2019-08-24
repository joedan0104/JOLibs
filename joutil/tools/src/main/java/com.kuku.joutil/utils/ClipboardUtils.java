package net.hmzs.tools.utils;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;

/**
 * Author: zhaolei
 * Date  : 2018/2/26
 * Desc  : 黏贴版工具类
 */

public class ClipboardUtils {
    /**
     * 将文字粘贴到复制板上
     *
     * @param content
     */
    @SuppressWarnings("deprecation")
    public static boolean copyToClipboard(Context context, String content) {
        if (content == null || "".equals(content)) {
            return false;
        }
        try {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) context
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, content.trim()));
            } else {
                android.text.ClipboardManager c = (android.text.ClipboardManager) context
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                c.setText(content.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
