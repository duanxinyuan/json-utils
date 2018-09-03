/**
 * android-studio-snooker
 * http://blog.csdn.net/qq_20698023
 * Dye-段新原
 */
package com.dxy.library.json.gson;

import com.dxy.library.json.gson.adapter.NumberTypeAdapter;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Gson工具类
 * 优势：数据量小（低于万）的时候速度有绝对优势
 * @author duanxinyuan
 * 2015/5/27 16:53
 */
@Slf4j
public class GsonUtil {
    private static Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss");
        gsonBuilder.disableHtmlEscaping();//禁止将部分特殊字符转义为unicode编码
        registTypeAdapter(gsonBuilder);
        gson = gsonBuilder.create();
    }

    private static void registTypeAdapter(GsonBuilder gsonBuilder) {
        gsonBuilder.registerTypeAdapter(short.class, new NumberTypeAdapter(short.class));
        gsonBuilder.registerTypeAdapter(Short.class, new NumberTypeAdapter(Short.class));
        gsonBuilder.registerTypeAdapter(int.class, new NumberTypeAdapter(int.class));
        gsonBuilder.registerTypeAdapter(Integer.class, new NumberTypeAdapter(Integer.class));
        gsonBuilder.registerTypeAdapter(long.class, new NumberTypeAdapter(long.class));
        gsonBuilder.registerTypeAdapter(Long.class, new NumberTypeAdapter(Long.class));
        gsonBuilder.registerTypeAdapter(float.class, new NumberTypeAdapter(float.class));
        gsonBuilder.registerTypeAdapter(Float.class, new NumberTypeAdapter(Float.class));
        gsonBuilder.registerTypeAdapter(double.class, new NumberTypeAdapter(double.class));
        gsonBuilder.registerTypeAdapter(Double.class, new NumberTypeAdapter(Double.class));
        gsonBuilder.registerTypeAdapter(BigDecimal.class, new NumberTypeAdapter(BigDecimal.class));
    }

    /**
     * JSON解析
     */
    public static <V> V from(Object jsonObj, Class<V> c) {
        return gson.fromJson(jsonObj.toString(), c);
    }

    /**
     * JSON解析
     */
    public static <V> V from(String json, Class<V> c) {
        return gson.fromJson(json, c);
    }

    /**
     * JSON解析
     */
    public static <V> V from(String json, Type type) {
        return gson.fromJson(json, type);
    }

    /**
     * JSON解析
     */
    public static <V> V from(String json, TypeToken<V> typeToken) {
        return gson.fromJson(json, typeToken.getType());
    }

    /**
     * 宽松JSON解析
     */
    public static <V> V lenientFrom(Object jsonObj, Class<V> c) {
        JsonReader reader = new JsonReader(new StringReader(jsonObj.toString()));
        reader.setLenient(true);
        return gson.fromJson(reader, c);
    }

    /**
     * 宽松JSON解析
     */
    public static <V> V lenientFrom(String json, Class<V> c) {
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        return gson.fromJson(reader, c);
    }

    /**
     * 宽松JSON解析
     */
    public static <V> V lenientFrom(String json, Type type) {
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        return gson.fromJson(reader, type);
    }

    /**
     * 宽松JSON解析
     */
    public static <V> V lenientFrom(String json, TypeToken<V> typeToken) {
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        return gson.fromJson(reader, typeToken.getType());
    }

    /**
     * 转化为JSON
     */
    public static <V> String to(ArrayList<V> list) {
        return gson.toJson(list);
    }

    /**
     * 转化为JSON
     */
    public static <V> String to(V v) {
        return gson.toJson(v);
    }

    /**
     * 从json串中获取某个字段
     * @return String
     */
    public static String getString(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        String propertyValue;
        JsonElement jsonByKey = getJsonByKey(json, key);
        if (jsonByKey == null) {
            return null;
        }
        try {
            propertyValue = jsonByKey.getAsString();
        } catch (Exception e) {
            propertyValue = jsonByKey.toString();
        }
        return propertyValue;
    }

    /**
     * 从json串中获取某个字段
     * @return int
     */
    public static Integer getInt(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        JsonElement jsonByKey = getJsonByKey(json, key);
        if (jsonByKey == null) {
            return null;
        }
        try {
            return jsonByKey.getAsInt();
        } catch (Exception e) {
            log.error("gson get int error, json: {}, key: {}", json, key, e);
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
        JsonElement jsonByKey = getJsonByKey(json, key);
        if (jsonByKey == null) {
            return null;
        }
        try {
            return jsonByKey.getAsLong();
        } catch (Exception e) {
            log.error("gson get long error, json: {}, key: {}", json, key, e);
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
        JsonElement jsonByKey = getJsonByKey(json, key);
        if (jsonByKey == null) {
            return null;
        }
        try {
            return jsonByKey.getAsDouble();
        } catch (Exception e) {
            log.error("gson get double error, json: {}, key: {}", json, key, e);
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
        JsonElement jsonByKey = getJsonByKey(json, key);
        if (jsonByKey == null) {
            return new BigInteger(String.valueOf(0.00));
        }
        try {
            return jsonByKey.getAsBigInteger();
        } catch (Exception e) {
            log.error("gson get biginteger error, json: {}, key: {}", json, key, e);
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
        JsonElement jsonByKey = getJsonByKey(json, key);
        if (jsonByKey == null) {
            return null;
        }
        try {
            return jsonByKey.getAsBigDecimal();
        } catch (Exception e) {
            log.error("gson get bigdecimal error, json: {}, key: {}", json, key, e);
            return null;
        }
    }

    /**
     * 从json串中获取某个字段
     * @return boolean, 默认为false
     */
    public static boolean getBoolean(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return false;
        }
        JsonElement jsonByKey = getJsonByKey(json, key);
        if (jsonByKey == null) {
            return false;
        }
        try {
            return jsonByKey.getAsBoolean();
        } catch (Exception e) {
            log.error("gson get boolean error, json: {}, key: {}", json, key, e);
            return false;
        }
    }

    /**
     * 从json串中获取某个字段
     * @return boolean, 默认为false
     */
    public static Byte getBytes(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        byte propertyValue;
        JsonElement jsonByKey = getJsonByKey(json, key);
        if (jsonByKey == null) {
            return null;
        }
        propertyValue = jsonByKey.getAsByte();
        return propertyValue;
    }

    /**
     * 从json串中获取某个字段
     * @return boolean, 默认为false
     */
    public static <T> ArrayList<T> getList(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        JsonElement jsonByKey = getJsonByKey(json, key);
        ArrayList<T> ts = null;
        if (jsonByKey != null) {
            try {
                JsonArray jsonArray = jsonByKey.getAsJsonArray();
                ts = from(jsonArray.toString(), new TypeToken<ArrayList<T>>() {});
            } catch (Exception e) {
                log.error("gson get list error, json: {}, key: {}", json, key, e);
            }
        }
        return ts;
    }

    private static JsonElement getJsonByKey(String json, String key) {
        JsonParser jsonParser = new JsonParser();
        JsonElement element;
        try {
            element = jsonParser.parse(json);
        } catch (JsonSyntaxException e) {
            log.error("gson get key from json error, json: {}, key: {}", json, key, e);
            return null;
        }
        JsonObject jsonObj = element.getAsJsonObject();
        return jsonObj.get(key);
    }

    /**
     * 向json中添加属性
     * @return json
     */
    public static <T> String add(String json, String key, T value) {
        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(json);
        JsonObject jsonObject = element.getAsJsonObject();
        add(jsonObject, key, value);
        return jsonObject.toString();
    }

    /**
     * 向json中添加属性
     */
    private static <T> void add(JsonObject jsonObject, String key, T value) {
        if (value instanceof String) {
            jsonObject.addProperty(key, (String) value);
        } else if (value instanceof Number) {
            jsonObject.addProperty(key, (Number) value);
        } else {
            jsonObject.addProperty(key, to(value));
        }
    }

    /**
     * 除去json中的某个属性
     * @return json
     */
    public static String remove(String json, String key) {
        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(json);
        JsonObject jsonObj = element.getAsJsonObject();
        jsonObj.remove(key);
        return jsonObj.toString();
    }

    /**
     * 修改json中的属性
     */
    public static <T> String update(String json, String key, T value) {
        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(json);
        JsonObject jsonObject = element.getAsJsonObject();
        jsonObject.remove(key);
        add(jsonObject, key, value);
        return jsonObject.toString();
    }

    /**
     * 格式化Json(美化)
     * @return json
     */
    public static String format(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(json);
        return gson.toJson(je);
    }

    /**
     * 判断字符串是否是json
     * @return json
     */
    public static boolean isJson(String json) {
        try {
            return new JsonParser().parse(json).isJsonObject();
        } catch (Exception e) {
            log.error("gson check json error, json: {}", json, e);
            return false;
        }
    }

}
