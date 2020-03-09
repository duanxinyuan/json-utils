package com.dxy.library.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dxy.library.json.fastjson.exception.FastjsonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

/**
 * FastJson工具类
 * 优势：
 * 数据量高于1万的时候速度有绝对优势
 * API简洁
 * @author duanxinyuan
 * 2018/6/28 22:55
 */
@Slf4j
public class FastjsonUtil {

    /**
     * JSON反序列化
     */
    public static <V> V from(InputStream inputStream, Class<V> type) {
        try {
            return JSON.parseObject(inputStream, type);
        } catch (IOException e) {
            log.error("fastjson from error, type: {}", type, e);
            throw new FastjsonException("fastjson from error, type: {}", type, e);
        }
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(InputStream inputStream, TypeReference<V> typeReference) {
        try {
            return JSON.parseObject(inputStream, typeReference.getType());
        } catch (IOException e) {
            log.error("fastjson from error, type: {}", typeReference, e);
            throw new FastjsonException("fastjson from error, type: {}", typeReference, e);
        }
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(String json, Class<V> type) {
        return JSON.parseObject(json, type);
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(String json, TypeReference<V> typeReference) {
        return JSON.parseObject(json, typeReference.getType());
    }

    /**
     * JSON反序列化（List）
     */
    public static <V> List<V> fromList(String json, Class<V> type) {
        return JSON.parseArray(json, type);
    }

    /**
     * JSON反序列化（Map）
     */
    public static HashMap<String, Object> fromMap(String json) {
        return JSON.parseObject(json, new TypeReference<HashMap<String, Object>>() {});
    }

    /**
     * 序列化为JSON
     */
    public static <V> String to(List<V> list) {
        return JSON.toJSONString(list);
    }

    /**
     * 序列化为JSON
     */
    public static <V> String to(V v) {
        return JSON.toJSONString(v);
    }

    /**
     * 从json串中获取某个字段
     * @return String，默认为 null
     */
    public static String getAsString(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return null;
            }
            return jsonObject.getString(key);
        } catch (Exception e) {
            log.error("fastjson get string error, json: {}, key: {}", json, key, e);
            throw new FastjsonException("fastjson get string error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * 从json串中获取某个字段
     * @return int，默认为 0
     */
    public static int getAsInt(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return 0;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return 0;
            }
            return jsonObject.getInteger(key);
        } catch (Exception e) {
            log.error("fastjson get int error, json: {}, key: {}", json, key, e);
            throw new FastjsonException("fastjson get int error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * 从json串中获取某个字段
     * @return long，默认为 0
     */
    public static long getAsLong(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return 0L;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return 0L;
            }
            return jsonObject.getLong(key);
        } catch (Exception e) {
            log.error("fastjson get long error, json: {}, key: {}", json, key, e);
            throw new FastjsonException("fastjson get long error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * 从json串中获取某个字段
     * @return double，默认为 0.0
     */
    public static double getAsDouble(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return 0.0;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return 0.0;
            }
            return jsonObject.getDouble(key);
        } catch (Exception e) {
            log.error("fastjson get double error, json: {}, key: {}", json, key, e);
            throw new FastjsonException("fastjson get double error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * 从json串中获取某个字段
     * @return BigInteger，默认为 0.0
     */
    public static BigInteger getAsBigInteger(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return new BigInteger(String.valueOf(0.00));
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return new BigInteger(String.valueOf(0.00));
            }
            return jsonObject.getBigInteger(key);
        } catch (Exception e) {
            log.error("fastjson get big integer error, json: {}, key: {}", json, key, e);
            throw new FastjsonException("fastjson get big integer error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * 从json串中获取某个字段
     * @return BigDecimal，默认为 0.0
     */
    public static BigDecimal getAsBigDecimal(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return new BigDecimal("0.0");
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return new BigDecimal("0.0");
            }
            return jsonObject.getBigDecimal(key);
        } catch (Exception e) {
            log.error("fastjson get big decimal error, json: {}, key: {}", json, key, e);
            throw new FastjsonException("fastjson get big decimal error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * 从json串中获取某个字段
     * @return boolean, 默认为false
     */
    public static boolean getAsBoolean(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return false;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return false;
            }
            return jsonObject.getBooleanValue(key);
        } catch (Exception e) {
            log.error("fastjson get boolean error, json: {}, key: {}", json, key, e);
            throw new FastjsonException("fastjson get boolean error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * 从json串中获取某个字段
     * @return byte, 默认为 0
     */
    public static byte getAsByte(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return 0;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return 0;
            }
            return jsonObject.getByteValue(key);
        } catch (Exception e) {
            log.error("fastjson get bytes error, json: {}, key: {}", json, key, e);
            throw new FastjsonException("fastjson get bytes error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * 从json串中获取某个字段
     * @return object, 默认为 null
     */
    public static <V> V getAsObject(String json, String key, Class<V> type) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return null;
            }
            return JSON.parseObject(jsonObject.getString(key), type);
        } catch (Exception e) {
            log.error("fastjson get list error, json: {}, key: {}, type: {}", json, key, type, e);
            throw new FastjsonException("fastjson get list error, json: {}, key: {}, type: {}", json, key, type, e);
        }
    }

    /**
     * 从json串中获取某个字段
     * @return list, 默认为 null
     */
    public static <V> List<V> getAsList(String json, String key, Class<V> type) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return null;
            }
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            return jsonArray.toJavaList(type);
        } catch (Exception e) {
            log.error("fastjson get list error, json: {}, key: {}, type: {}", json, key, type, e);
            throw new FastjsonException("fastjson get list error, json: {}, key: {}, type: {}", json, key, type, e);
        }
    }

    /**
     * 从json串中获取某个字段
     * @return JSONObject, 默认为 null
     */
    public static JSONObject getAsJsonObject(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            if (jsonObject == null) {
                return null;
            }
            return jsonObject.getJSONObject(key);
        } catch (Exception e) {
            log.error("fastjson get list error, json: {}, key: {}", json, key, e);
            throw new FastjsonException("fastjson get list error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * 向json中添加属性
     * @return json
     */
    public static <V> String add(String json, String key, V value) {
        JSONObject jsonObject = JSON.parseObject(json);
        add(jsonObject, key, value);
        return jsonObject.toString();
    }

    /**
     * 向json中添加属性
     */
    private static <V> void add(JSONObject jsonObject, String key, V value) {
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
        JSONObject jsonObject = JSON.parseObject(json);
        jsonObject.remove(key);
        return jsonObject.toString();
    }

    /**
     * 修改json中的属性
     */
    public static <V> String update(String json, String key, V value) {
        JSONObject jsonObject = JSON.parseObject(json);
        add(jsonObject, key, value);
        return jsonObject.toString();
    }

    /**
     * 格式化Json(美化)
     * @return json
     */
    public static String format(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        return JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat);
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
            log.error("fastjson check json error, json: {}", json, e);
            return false;
        }
    }
}
