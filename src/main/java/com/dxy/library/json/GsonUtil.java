/**
 * android-studio-snooker
 * http://blog.csdn.net/qq_20698023
 * Dye-段新原
 */
package com.dxy.library.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Gson解析工具类
 * @author duanxinyuan
 * 2015/5/27 16:53
 */
@Slf4j
public class GsonUtil {
    private static Gson gson;
    private static Gson exposeGson;

    /**
     * json中对于Html的转义,new Gson()默认对Html进行转义，如果不想转义使用下面的方法
     * @return gson
     */
    public Gson getHtmlGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.disableHtmlEscaping();
        return builder.create();
    }

    /**
     * json中日期格式的处理，使用该对象进行json的处理，如果出现日期Date类的对象，就会按照设置的格式进行处理
     * @param format 时间格式
     * @return gson
     */
    public static Gson getDateGson(String format) {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat(format);
        return builder.create();
    }

    /**
     * 格式化Json
     * @return json
     */
    public static String formatJson(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(json);
        return gson.toJson(je);
    }

    public static Gson getGson() {
        if (null == gson) {
            synchronized (GsonUtil.class) {
                if (gson == null) {
                    gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                }
            }
        }
        return gson;
    }

    public static Gson getExposeGson() {
        if (null == exposeGson) {
            exposeGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        }
        return exposeGson;
    }

    public static <V> V from(Object jsonObj, Class<V> c) {
        return getGson().fromJson(jsonObj.toString(), c);
    }

    public static <V> V from(String json, Class<V> c) {
        return getGson().fromJson(json, c);
    }

    public static <V> V from(String json, Type type) {
        return getGson().fromJson(json, type);
    }

    public static <V> V from(String json, TypeToken<V> typeToken) {
        return getGson().fromJson(json, typeToken.getType());
    }

    public static <V> String to(ArrayList<V> list) {
        return getGson().toJson(list);
    }

    public static <V> String to(V v) {
        return getGson().toJson(v);
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
        JsonElement jsonByKey = getJsonByKey(json, key);
        if (jsonByKey == null) {
            return null;
        }
        try {
            return jsonByKey.getAsLong();
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
        JsonElement jsonByKey = getJsonByKey(json, key);
        if (jsonByKey == null) {
            return null;
        }
        try {
            return jsonByKey.getAsDouble();
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
        JsonElement jsonByKey = getJsonByKey(json, key);
        if (jsonByKey == null) {
            return new BigInteger(String.valueOf(0.00));
        }
        try {
            return jsonByKey.getAsBigInteger();
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
        JsonElement jsonByKey = getJsonByKey(json, key);
        if (jsonByKey == null) {
            return null;
        }
        try {
            return jsonByKey.getAsBigDecimal();
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
        JsonElement jsonByKey = getJsonByKey(json, key);
        if (jsonByKey == null) {
            return false;
        }
        try {
            return jsonByKey.getAsBoolean();
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
        byte propertyValue;
        JsonElement jsonByKey = getJsonByKey(json, key);
        if (jsonByKey == null || "null".equals(jsonByKey.toString()) || StringUtils.isEmpty(jsonByKey.toString())) {
            return null;
        }
        propertyValue = jsonByKey.getAsByte();
        return propertyValue;
    }

    /**
     * 从json串中获取某个字段
     * @return boolean，默认为false
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
                log.error("从json串中获取数组失败，Json内容：{}，Key：{}", json, key, e);
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
            log.error("从json串中获取字段失败，Json内容：{}，Key：{}", json, key, e);
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
     * @return json
     */
    private static <T> String add(JsonObject jsonObject, String key, T value) {
        if (value instanceof String) {
            jsonObject.addProperty(key, (String) value);
        } else if (value instanceof Integer || value instanceof Long) {
            jsonObject.addProperty(key, (Number) value);
        } else {
            jsonObject.addProperty(key, to(value));
        }
        return jsonObject.toString();
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
     * 判断字符串是否是json
     * @return json
     */
    public static boolean isJson(String json) {
        try {
            return new JsonParser().parse(json).isJsonObject();
        } catch (Exception e) {
            log.error("判断字符串是否是json失败，Json内容：{}", json, e);
            return false;
        }
    }
}
