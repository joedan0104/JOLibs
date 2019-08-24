package net.hmzs.tools.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/16
 *     desc  : 字符串相关工具类
 * </pre>
 */
public final class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断字符串是否为 null 或长度为 0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(final CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串是否为 null 或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null 或全空格<br> {@code false}: 不为 null 且不全空格
     */
    public static boolean isTrimEmpty(final String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断字符串是否为 null 或全为空白字符
     *
     * @param s 待校验字符串
     * @return {@code true}: null 或全空白字符<br> {@code false}: 不为 null 且不全空白字符
     */
    public static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串 a
     * @param b 待校验字符串 b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(final CharSequence a, final CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串 a
     * @param b 待校验字符串 b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(final String a, final String b) {
        return a == null ? b == null : a.equalsIgnoreCase(b);
    }

    /**
     * null 转为长度为 0 的字符串
     *
     * @param s 待转字符串
     * @return s 为 null 转为长度为 0 字符串，否则不改变
     */
    public static String null2Length0(final String s) {
        return s == null ? "" : s;
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null 返回 0，其他返回自身长度
     */
    public static int length(final CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(final String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(final String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(final String s) {
        int len = length(s);
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String full2Half(final String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String half2Full(final String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 拼接字符串
     */
    public static String join(Object[] tokens, CharSequence delimiter) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token : tokens) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     */
    public static String replaceBlank(String str) {
        if (!isEmpty(str)) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            str = m.replaceAll("");
        }
        return str;
    }

    /**
     * 删除所有的标点符号
     */
    public static String trimPunctuation(String str) {
        return str.replaceAll("[\\pP\\p{Punct}]", "");
    }

    /**
     * 格式化一个float
     *
     * @param format
     *         要格式化成的格式 such as #.00, #.#
     */
    public static String formatFloat(float f, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(f);
    }

    /**
     * 将list 用传入的分隔符组装为String
     */
    public static String listToStringSlipStr(List list, String slipStr) {
        StringBuilder builder = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                builder.append(list.get(i)).append(slipStr);
            }
        }
        if (builder.toString().length() > 0) {
            return builder.toString().substring(0, builder.toString().lastIndexOf(slipStr));
        } else {
            return "";
        }
    }

    /**
     * 全角括号转为半角
     */
    public static String replaceBracketStr(String str) {
        if (!isEmpty(str)) {
            str = str.replaceAll("（", "(");
            str = str.replaceAll("）", ")");
        }
        return str;
    }

    /**
     * 解析字符串返回map键值对(例：a=1&b=2 => a=1,b=2)
     *
     * @param query
     *         源参数字符串
     * @param split1
     *         键值对之间的分隔符（例：&）
     * @param split2
     *         key与value之间的分隔符（例：=）
     * @param dupLink
     *         重复参数名的参数值之间的连接符，连接后的字符串作为该参数的参数值，可为null
     *         null：不允许重复参数名出现，则靠后的参数值会覆盖掉靠前的参数值。
     *
     * @return map
     */
    public static Map<String, String> parseQuery(String query, char split1, char split2, String dupLink) {
        if (!isEmpty(query) && query.indexOf(split2) > 0) {
            Map<String, String> result = new HashMap<>();

            String name = null;
            String value = null;
            String tempValue;
            for (int i = 0; i < query.length(); i++) {
                char c = query.charAt(i);
                if (c == split2) {
                    value = "";
                } else if (c == split1) {
                    if (!isEmpty(name) && value != null) {
                        if (dupLink != null) {
                            tempValue = result.get(name);
                            if (tempValue != null) {
                                value += dupLink + tempValue;
                            }
                        }
                        result.put(name, value);
                    }
                    name = null;
                    value = null;
                } else if (value != null) {
                    value += c;
                } else {
                    name = (name != null) ? (name + c) : "" + c;
                }
            }

            if (!isEmpty(name) && value != null) {
                if (dupLink != null) {
                    tempValue = result.get(name);
                    if (tempValue != null) {
                        value += dupLink + tempValue;
                    }
                }
                result.put(name, value);
            }
            return result;
        }
        return null;
    }

    public static String stripHtml(String content) {
        // <p>段落替换为换行
        content = content.replaceAll("<p .*?>", "\r\n");
        // <br><br/>替换为换行
        content = content.replaceAll("<br\\s*/?>", "\r\n");
        // 去掉其它的<>之间的东西
        content = content.replaceAll("\\<.*?>", "");
        // 还原HTML
        // content = HTMLDecoder.decode(content);
        return content;
    }

    /**
     * 截取字符串中的数值部分
     */
    public static String substringNumber(String params) {
        Matcher matcher = Pattern.compile("[0-9,.%]+").matcher(params);
        if (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            return params.substring(start, end);
        } else {
            return "0";
        }
    }

    /**
     * 从字符串中获取 boolean 型
     */
    public static boolean getBoolean(String args) {
        if (!TextUtils.isEmpty(args)) {
            if (args.equals("true")) {
                return true;
            } else if (args.equals("false")) {
                return false;
            } else if (RegexUtils.isNumeric(args)) {
                return getInteger(args) > 0;
            }
        }
        return false;
    }

    /**
     * 从字符串中获取 int 型，如果字符串为空，则返回0
     */
    public static int getInteger(String args) {
        try {
            if (TextUtils.isEmpty(args)) {
                return 0;
            } else {
                return new BigDecimal(args).intValue();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 从字符串中获取 short 型，如果字符串为空，则返回0
     */
    public static int getShort(String args) {
        try {
            if (TextUtils.isEmpty(args)) {
                return 0;
            } else {
                return new BigDecimal(args).shortValue();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 从字符串中获取 long 型，如果字符串为空，则返回0
     */
    public static long getLong(String args) {
        try {
            if (TextUtils.isEmpty(args)) {
                return 0L;
            } else {
                return new BigDecimal(args).longValue();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 从字符串中获取 float 型，如果字符串为空，则返回0
     */
    public static float getFloat(String args) {
        try {
            if (TextUtils.isEmpty(args)) {
                return 0.0F;
            } else {
                return new BigDecimal(args).floatValue();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0F;
        }
    }

    /**
     * @param args
     *         源字符串
     * @param scale
     *         保留精度
     *
     * @return 从字符串中获取 float 型，如果字符串为空，则返回0
     */
    public static float getFloat(String args, int scale) {
        try {
            if (TextUtils.isEmpty(args)) {
                return 0.0F;
            } else {
                return new BigDecimal(args).setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0F;
        }
    }

    /**
     * 从字符串中获取 double 型，如果字符串为空，则返回0
     */
    public static double getDouble(String args) {
        try {
            if (TextUtils.isEmpty(args)) {
                return 0.0D;
            } else {
                return new BigDecimal(args).doubleValue();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0D;
        }
    }

    /**
     * @param args
     *         源字符串
     * @param scale
     *         保留精度
     *
     * @return 从字符串中获取 double 型，如果字符串为空，则返回0
     */
    public static double getDouble(String args, int scale) {
        try {
            if (TextUtils.isEmpty(args)) {
                return 0.0D;
            } else {
                return new BigDecimal(args).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0D;
        }
    }

    /**
     * 数组中是否有包含args的元素
     * @param strArray
     * @param args
     * @return
     */
    public static boolean containStr(String[] strArray, String args) {
        int size = 0;
        if (null != strArray) {
            size = strArray.length;
        }
        for (int i = 0; i < size; i++) {
            if (strArray[i].contains(args)) {
                return true;
            }
        }
        return false;
    }

    /**
     * args中是否包含数组中的元素
     * @param args
     * @param strArray
     * @return
     */
    public static boolean containStr(String args, String[] strArray) {
        int size = 0;
        if (null != strArray) {
            size = strArray.length;
        }
        for (int i = 0; i < size; i++) {
            if (args.contains(strArray[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取字符串值（空值转化为""）
     * 说明: 将空串或者null转换为空字符串，防止字符串拼接时出问题
     */
    public static String stringNoNil(String value) {
        StringBuilder builder = new StringBuilder();
        if(!TextUtils.isEmpty(value)) {
            builder.append(value);
        }
        return builder.toString();
    }
}
