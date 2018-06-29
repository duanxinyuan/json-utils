package com.dxy.library.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Fastjson工具类
 * @author duanxinyuan
 * 2018/6/28 22:55
 */
@Slf4j
public class FastjsonUtl {

    /**
     * JSON解析
     */
    public static <V> V from(Object jsonObj, Class<V> c) {
        return JSON.parseObject(jsonObj.toString(), c);
    }

    /**
     * JSON解析
     */
    public static <V> V from(String json, Class<V> c) {
        return JSON.parseObject(json, c);
    }

    /**
     * JSON解析
     */
    public static <V> V from(String json, TypeReference<V> typeToken) {
        return JSON.parseObject(json, typeToken.getType());
    }

    /**
     * 转化为JSON
     */
    public static <V> String to(ArrayList<V> list) {
        return JSON.toJSONString(list);
    }

    /**
     * 转化为JSON
     */
    public static <V> String to(V v) {
        return JSON.toJSONString(v);
    }

    /**
     * 从json串中获取某个字段
     * @return String
     */
    public static String getString(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (jsonObject == null) {
            return null;
        }
        try {
            return jsonObject.getString(key);
        } catch (Exception e) {
            log.error("从json串中获取字段失败，Json内容：{}，Key：{}", json, key, e);
            return null;
        }
    }

    /**
     * 从json串中获取某个字段
     * @return int
     */
    public static Integer getInt(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (jsonObject == null) {
            return null;
        }
        try {
            return jsonObject.getInteger(key);
        } catch (Exception e) {
            log.error("从json串中获取字段失败，Json内容：{}，Key：{}", json, key, e);
            return null;
        }
    }

    /**
     * 从json串中获取某个字段
     * @return long
     */
    public static Long getLong(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (jsonObject == null) {
            return null;
        }
        try {
            return jsonObject.getLong(key);
        } catch (Exception e) {
            log.error("从json串中获取字段失败，Json内容：{}，Key：{}", json, key, e);
            return null;
        }
    }

    /**
     * 从json串中获取某个字段
     * @return double
     */
    public static Double getDouble(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (jsonObject == null) {
            return null;
        }
        try {
            return jsonObject.getDouble(key);
        } catch (Exception e) {
            log.error("从json串中获取字段失败，Json内容：{}，Key：{}", json, key, e);
            return null;
        }
    }

    /**
     * 从json串中获取某个字段
     * @return double
     */
    public static BigInteger getBigInteger(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return new BigInteger(String.valueOf(0.00));
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (jsonObject == null) {
            return new BigInteger(String.valueOf(0.00));
        }
        try {
            return jsonObject.getBigInteger(key);
        } catch (Exception e) {
            log.error("从json串中获取字段失败，Json内容：{}，Key：{}", json, key, e);
            return null;
        }
    }

    /**
     * 从json串中获取某个字段
     * @return double
     */
    public static BigDecimal getBigDecimal(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (jsonObject == null) {
            return null;
        }
        try {
            return jsonObject.getBigDecimal(key);
        } catch (Exception e) {
            log.error("从json串中获取字段失败，Json内容：{}，Key：{}", json, key, e);
            return null;
        }
    }

    /**
     * 从json串中获取某个字段
     * @return boolean，默认为false
     */
    public static boolean getBoolean(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return false;
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (jsonObject == null) {
            return false;
        }
        try {
            return jsonObject.getBooleanValue(key);
        } catch (Exception e) {
            log.error("从json串中获取字段失败，Json内容：{}，Key：{}", json, key, e);
            return false;
        }
    }

    /**
     * 从json串中获取某个字段
     * @return boolean，默认为false
     */
    public static Byte getByte(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (jsonObject == null) {
            return null;
        }
        return jsonObject.getByteValue(key);
    }

    /**
     * 从json串中获取某个字段
     * @return boolean，默认为false
     */
    public static <T> List<T> getList(String json, String key, Class<T> c) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        List<T> ts = null;
        if (jsonObject != null) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(key);
                ts = jsonArray.toJavaList(c);
            } catch (Exception e) {
                log.error("从json串中获取数组失败，Json内容：{}，Key：{}", json, key, e);
            }
        }
        return ts;
    }

    /**
     * 向json中添加属性
     * @return json
     */
    public static <T> String add(String json, String key, T value) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        add(jsonObject, key, value);
        return jsonObject.toString();
    }

    /**
     * 向json中添加属性
     * @return json
     */
    private static <T> void add(JSONObject jsonObject, String key, T value) {
        if (value instanceof String || value instanceof Number || value instanceof Boolean || value instanceof Byte[]) {
            jsonObject.put(key, value);
        } else {
            jsonObject.put(key, to(value));
        }
    }

    /**
     * 除去json中的某个属性
     * @return json
     */
    public static String remove(String json, String key) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        jsonObject.remove(key);
        return jsonObject.toString();
    }

    /**
     * 修改json中的属性
     */
    public static <T> String update(String json, String key, T value) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        add(jsonObject, key, value);
        return jsonObject.toString();
    }

    /**
     * 判断字符串是否是json
     * @return json
     */
    public static boolean isJson(String json) {
        try {
            JSON.parse(json);
            return true;
        } catch (Exception e) {
            log.error("判断字符串是否是json失败，Json内容：{}", json, e);
            return false;
        }
    }
}
