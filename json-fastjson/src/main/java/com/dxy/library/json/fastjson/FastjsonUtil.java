package com.dxy.library.json.fastjson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;

import com.dxy.library.json.fastjson.exception.FastjsonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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

    static {
        //屏蔽JSON中的 $ref
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
        //序列化没有set方法的private字段
        JSON.DEFAULT_PARSER_FEATURE |= Feature.SupportNonPublicField.getMask();
        //忽略get方法抛出的异常
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.IgnoreErrorGetter.getMask();
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(InputStream inputStream, Class<V> type) {
        try {
            return JSON.parseObject(inputStream, type);
        } catch (IOException e) {
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
     * JSON反序列化（Set）
     */
    public static <V> Set<V> fromSet(String json, Class<V> type) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return JSON.parseObject(json, new ParameterizedTypeImpl(new Type[] {type}, null, Set.class));
    }

    /**
     * JSON反序列化（List）
     */
    public static <V> List<V> fromList(String json, Class<V> type) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return JSON.parseArray(json, type);
    }

    /**
     * JSON反序列化（Map）
     */
    public static <K, V> Map<K, V> fromMap(String json, Class<K> keyType, Class<V> valueType) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return JSON.parseObject(json, new ParameterizedTypeImpl(new Type[] {keyType, valueType}, null, Map.class));
    }

    /**
     * JSON反序列化（Map<K, List<V>>）
     */
    public static <K, V> Map<K, List<V>> fromListMap(String json, Class<K> keyType, Class<V> valueType) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        ParameterizedTypeImpl valueParameterizedType = new ParameterizedTypeImpl(new Type[] {valueType}, null,
            List.class);
        return JSON.parseObject(json,
            new ParameterizedTypeImpl(new Type[] {keyType, valueParameterizedType}, null, Map.class));
    }

    /**
     * JSON反序列化（Map<K, Set<V>>）
     */
    public static <K, V> Map<K, Set<V>> fromSetMap(String json, Class<K> keyType, Class<V> valueType) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        ParameterizedTypeImpl valueParameterizedType = new ParameterizedTypeImpl(new Type[] {valueType}, null,
            Set.class);
        return JSON.parseObject(json,
            new ParameterizedTypeImpl(new Type[] {keyType, valueParameterizedType}, null, Map.class));
    }

    /**
     * JSON反序列化（List<Map<K, V>>）
     */
    public static <K, V> List<Map<K, V>> fromMapList(String json, Class<K> keyType, Class<V> valueType) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        ParameterizedTypeImpl valueParameterizedType = new ParameterizedTypeImpl(new Type[] {keyType, valueType}, null,
            Map.class);
        return JSON.parseObject(json,
            new ParameterizedTypeImpl(new Type[] {valueParameterizedType}, null, List.class));
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
            throw new FastjsonException("fastjson get list error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * 向json中添加属性
     * @return json
     */
    public static <V> String add(String json, String key, V value) {
        Object object = JSON.parse(json);
        if (object instanceof JSONObject) {
            add((JSONObject)object, key, value);
        }
        return object.toString();
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
        Object object = JSON.parse(json);
        if (object instanceof JSONObject) {
            ((JSONObject)object).remove(key);
        }
        return object.toString();
    }

    /**
     * 修改json中的属性
     */
    public static <V> String update(String json, String key, V value) {
        Object object = JSON.parse(json);
        if (object instanceof JSONObject) {
            add((JSONObject)object, key, value);
            ((JSONObject)object).remove(key);
        }
        return object.toString();
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
            return false;
        }
    }
}
