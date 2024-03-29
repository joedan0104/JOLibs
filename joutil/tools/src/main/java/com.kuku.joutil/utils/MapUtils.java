package net.hmzs.tools.utils;

import java.util.Map;
import java.util.TreeMap;

/**
 * Author: Chenming
 * E-mail: cm1@erongdu.com
 * Date: 2016/12/13 下午2:25
 * <p/>
 * Description: Map、json互相转换
 */
public class MapUtils {
    /**
     * map 转换为 json
     *
     * @param data
     *
     * @return
     */
    public static String mapData2Json(Map<String, Object> data) {
        String json = "{";
        for (String key : data.keySet()) {
            if (isString(data.get(key))) {
                json = json + "\"" + key + "\":\"" + data.get(key) + "\",";
            } else {
                json = json + "\"" + key + "\":" + data.get(key) + ",";
            }
        }
        json = json.substring(0, json.length() - 1) + "}";
        return json;
    }

    /**
     * 判断是否是为String
     *
     * @param object
     *
     * @return
     */
    private static boolean isString(Object object) {
        return object instanceof String;
    }

    /**
     * 删除 map中空键值
     *
     * @param map
     */
    public static TreeMap<String, Object> removeNull(Map<String, Object> map) {
        TreeMap<String, Object> newMap = new TreeMap<>();
        if(map == null || map.size() == 0){
            return  newMap;
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                newMap.put(entry.getKey(), entry.getValue());
            }
        }
        return newMap;
    }
}
