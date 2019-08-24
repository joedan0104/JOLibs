package net.hmzs.tools.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.google.gson.Gson;

import net.hmzs.tools.encryption.Base64;
import net.hmzs.tools.encryption.DES3;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static net.hmzs.tools.constant.SPConstants.SP_FILE_APP;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/23 19:30
 * <p/>
 * Description: SharePreference 工具类
 *
 * 2.0 采用单例模式；扩展多个SP文件的模式
 */
@SuppressWarnings("unused")
public class SPUtils {
    /**
     * 外部使用对象
     */
    public static SPUtils SPTOOL = SPUtils.getInstance();
    /**
     * 3des 加解密的key
     */
    private String SECRET_KEY;

    /**
     * SP对象列表
     */
    private Map<String, SharedPreferences> mapSp = new HashMap<String, SharedPreferences>();
    /**
     * 单例对象
     */
    private static SPUtils sInstance = null;

    private SPUtils() {

    }

    /**
     * 获取SPUtils单例对象
     */
    public static SPUtils getInstance() {
        if (null == sInstance) {
            synchronized (SharedPreferences.class) {
                if (null == sInstance) {
                    sInstance = new SPUtils();
                }
            }
        }
        return sInstance;
    }

    /**
     * 设置加密秘钥
     *
     * @param secretKey : 秘钥
     */
    public void setSecretKey(String secretKey) {
        this.SECRET_KEY = secretKey;
    }

    /**
     * SPUtils 初始化
     *
     * @param sp_name : SP文件名
     * @return
     */
    public SharedPreferences getSp(String sp_name) {
        if (!mapSp.containsKey(sp_name)) {
            SharedPreferences sp = ContextHolder.getContext().getSharedPreferences(sp_name,
                    Context.MODE_PRIVATE);
            mapSp.put(sp_name, sp);
        }
        return mapSp.get(sp_name);
    }

    /**
     * 保存SP键值对(APP相关的数据)
     *
     * @param key   : 键
     * @param value : 值
     * @return
     */
    public boolean saveValue(String key, Object value) {
        return saveValue(SP_FILE_APP, key, value);
    }

    /**
     * 保存SP键值对
     *
     * @param sp_name : SP文件名
     * @param key     : 键
     * @param value   : 值
     * @return
     */
    public boolean saveValue(String sp_name, String key, Object value) {
        return saveValue(getSp(sp_name), key, value);
    }

    /**
     * 保存到 SharePreference 中
     */
    public boolean saveValue(SharedPreferences sp, String key, Object value) {
        if (null == sp) {
            return false;
        }
        SharedPreferences.Editor editor = sp.edit();

        if (value instanceof String) {
            return editor.putString(key, (String) value).commit();
        } else if (value instanceof Boolean) {
            return editor.putBoolean(key, (Boolean) value).commit();
        } else if (value instanceof Float) {
            return editor.putFloat(key, (Float) value).commit();
        } else if (value instanceof Integer) {
            return editor.putInt(key, (Integer) value).commit();
        } else if (value instanceof Long) {
            return editor.putLong(key, (Long) value).commit();
        } else if (value instanceof Set) {
            throw new IllegalArgumentException("Value can not be Set object!");
        }
        return false;
    }

    /**
     * 获取键值对(默认APP的SP文件中)
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public Object getValue(String key, Object defaultValue) {
        return getValue(SP_FILE_APP, key, defaultValue);
    }

    /**
     * 获取键值对(对应的SP文件中)
     *
     * @param sp_name
     * @param key
     * @param defaultValue
     * @return
     */
    public Object getValue(String sp_name, String key, Object defaultValue) {
        return getValue(getSp(sp_name), key, defaultValue);
    }

    /**
     * 从 SharePreference 中取值
     */
    public Object getValue(SharedPreferences sp, String key, Object defaultValue) {
        if (null == sp) {
            return null;
        }
        if (defaultValue instanceof String) {
            return sp.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sp.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sp.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sp.getLong(key, (Long) defaultValue);
        } else if (defaultValue instanceof Set) {
            throw new IllegalArgumentException("Can not to get Set value!");
        }
        return null;
    }

    /**
     * 查询某个key是否已经存在
     *
     * @return 是否存在
     */
    public boolean contains(String key) {
        return contains(SP_FILE_APP, key);
    }


    /**
     * 查询某个key是否已经存在
     *
     * @return 是否存在
     */
    public boolean contains(String sp_name, String key) {
        return contains(getSp(sp_name), key);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @return 是否存在
     */
    private static boolean contains(SharedPreferences sp, String key) {
        return null != sp && sp.contains(key);
    }

    /**
     * 移除某个key值已经对应的值
     */
    public boolean remove(String key) {
        return remove(SP_FILE_APP, key);
    }

    /**
     * 移除某个key值已经对应的值
     */
    public boolean remove(String sp_name, String key) {
        return remove(getSp(sp_name), key);
    }

    /**
     * 移除某个key值已经对应的值
     */
    private boolean remove(SharedPreferences sp, String key) {
        if (null == sp) {
            return false;
        }
        Editor editor = sp.edit();
        editor.remove(key);
        return editor.commit();
    }

    /**
     * 清除所有数据
     *
     * @return 是否成功
     */
    public boolean clear(String sp_name) {
        return clear(getSp(sp_name));
    }

    /**
     * 清除所有数据
     *
     * @return 是否成功
     */
    private boolean clear(SharedPreferences sp) {
        if (null == sp) {
            return false;
        }
        Editor editor = sp.edit();
        editor.clear();
        return editor.commit();
    }

    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll(SharedPreferences sp) {
        if (null == sp) {
            return null;
        }
        return sp.getAll();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Object 以 JSON 的形式存入SP
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 保存类对象
     *
     * @param obj : 对象
     * @return 是否保存成功
     */
    public boolean saveEntity(String sp_key, final Object obj) {
        return saveEntity(SP_FILE_APP, sp_key, obj);
    }

    /**
     * 保存类对象
     *
     * @param sp_name : SP文件名
     * @param obj     : 对象
     * @return 是否保存成功
     */
    public boolean saveEntity(String sp_name, String sp_key, final Object obj) {
        return saveEntity(getSp(sp_name), sp_key, obj);
    }

    /**
     * @param obj 对象
     * @return 是否保存成功
     */
    private boolean saveEntity(SharedPreferences sp, String sp_key, final Object obj) {
        if (null != obj) {
            if (!TextUtils.isEmpty(sp_key)) {
                String value = obj2str(obj);
                if (TextUtils.isEmpty(value)) {
                    return false;
                }
                if (!TextUtils.isEmpty(SECRET_KEY)) {
                    try {
                        value = DES3.encrypt(SECRET_KEY, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                        value = Base64.encode(value.getBytes());
                    }
                } else {
                    value = Base64.encode(value.getBytes());
                }
                return saveValue(sp, sp_key, value);
            }
        }
        return false;
    }

    /**
     * @param clazz        类型
     * @param defaultValue 默认值
     * @return T对象
     */
    public <T> T getEntity(String sp_key, final Class<T> clazz, final T defaultValue) {
        return getEntity(SP_FILE_APP, sp_key, clazz, defaultValue);
    }

    /**
     * @param clazz        类型
     * @param defaultValue 默认值
     * @return T对象
     */
    public <T> T getEntity(String sp_name, String sp_key, final Class<T> clazz, final T defaultValue) {
        return getEntity(getSp(sp_name), sp_key, clazz, defaultValue);
    }

    /**
     * @param clazz        类型
     * @param defaultValue 默认值
     * @return T对象
     */
    public <T> T getEntity(SharedPreferences sp, String sp_key, final Class<T> clazz, final T defaultValue) {
        if (!TextUtils.isEmpty(sp_key)) {
            String value = (String) getValue(sp, sp_key, "");
            if (TextUtils.isEmpty(value)) {
                return null;
            }
            if (!TextUtils.isEmpty(SECRET_KEY)) {
                try {
                    value = DES3.decrypt(SECRET_KEY, value);
                } catch (Exception e) {
                    e.printStackTrace();
                    value = new String(Base64.decode(value));
                }
            } else {
                value = new String(Base64.decode(value));
            }
            T ret = str2obj(value, clazz);
            if (null != ret) {
                return ret;
            }
        }
        return defaultValue;
    }

    /**
     * 类对应的key
     */
    private String getKey(final Class<?> clazz) {
        if (null != clazz) {
            return clazz.getName();
        }
        return null;
    }

    /***
     * Object 到 String 的序列化
     */
    private String obj2str(final Object obj) {
        try {
            return new Gson().toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * String 到 Object 的反序列化
     */
    private <T> T str2obj(final String string, final Class<T> clazz) {
        try {
            return new Gson().fromJson(string, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
