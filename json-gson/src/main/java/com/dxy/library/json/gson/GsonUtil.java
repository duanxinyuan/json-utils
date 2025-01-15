package com.dxy.library.json.gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.dxy.library.json.gson.adapter.NumberTypeAdapter;
import com.dxy.library.json.gson.exception.GsonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Gson工具类
 * 优势：
 * 数据量低于1万的时候速度有绝对优势
 * API和注解支持较为完善，支持宽松解析
 * 支持的数据源较广泛（字符串，对象，文件、流）
 * @author duanxinyuan
 * 2015/5/27 16:53
 */
@Slf4j
public class GsonUtil {
    private static final Gson GSON;
    private static final Gson GSON_PRETTY;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss");
        gsonBuilder.disableHtmlEscaping();//禁止将部分特殊字符转义为unicode编码
        registerTypeAdapter(gsonBuilder);
        GSON = gsonBuilder.create();
        GSON_PRETTY = new GsonBuilder().setPrettyPrinting().create();
    }

    private static void registerTypeAdapter(GsonBuilder gsonBuilder) {
        gsonBuilder.registerTypeAdapter(short.class, new NumberTypeAdapter<>(short.class));
        gsonBuilder.registerTypeAdapter(Short.class, new NumberTypeAdapter<>(Short.class));
        gsonBuilder.registerTypeAdapter(int.class, new NumberTypeAdapter<>(int.class));
        gsonBuilder.registerTypeAdapter(Integer.class, new NumberTypeAdapter<>(Integer.class));
        gsonBuilder.registerTypeAdapter(long.class, new NumberTypeAdapter<>(long.class));
        gsonBuilder.registerTypeAdapter(Long.class, new NumberTypeAdapter<>(Long.class));
        gsonBuilder.registerTypeAdapter(float.class, new NumberTypeAdapter<>(float.class));
        gsonBuilder.registerTypeAdapter(Float.class, new NumberTypeAdapter<>(Float.class));
        gsonBuilder.registerTypeAdapter(double.class, new NumberTypeAdapter<>(double.class));
        gsonBuilder.registerTypeAdapter(Double.class, new NumberTypeAdapter<>(Double.class));
        gsonBuilder.registerTypeAdapter(BigDecimal.class, new NumberTypeAdapter<>(BigDecimal.class));
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(InputStream inputStream, Class<V> type) {
        JsonReader reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(inputStream)));
        return GSON.fromJson(reader, type);
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(InputStream inputStream, TypeToken<V> typeToken) {
        JsonReader reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(inputStream)));
        return GSON.fromJson(reader, typeToken.getType());
    }

    /**
     * JSON反序列化（List）
     */
    public static <V> List<V> fromList(InputStream inputStream, Class<V> type) {
        JsonReader reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(inputStream)));
        TypeToken<List<V>> typeToken = (TypeToken<List<V>>)TypeToken.getParameterized(ArrayList.class, type);
        return GSON.fromJson(reader, typeToken.getType());
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(File file, Class<V> type) {
        try {
            JsonReader reader = new JsonReader(new FileReader(file));
            return GSON.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            throw new GsonException("gson from error, file path: {}, type: {}", file.getPath(), type, e);
        }
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(File file, TypeToken<V> typeToken) {
        try {
            JsonReader reader = new JsonReader(new FileReader(file));
            return GSON.fromJson(reader, typeToken.getType());
        } catch (FileNotFoundException e) {
            throw new GsonException("gson from error, file path: {}, type: {}", file.getPath(), typeToken.getType(), e);
        }
    }

    /**
     * JSON反序列化（List）
     */
    public static <V> List<V> fromList(File file, Class<V> type) {
        try {
            JsonReader reader = new JsonReader(new FileReader(file));
            TypeToken<List<V>> typeToken = (TypeToken<List<V>>)TypeToken.getParameterized(ArrayList.class, type);
            return GSON.fromJson(reader, typeToken.getType());
        } catch (FileNotFoundException e) {
            throw new GsonException("gson from error, file path: {}, type: {}", file.getPath(), type, e);
        }
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(String json, Class<V> type) {
        return GSON.fromJson(json, type);
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(String json, Type type) {
        return GSON.fromJson(json, type);
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(String json, TypeToken<V> typeToken) {
        return GSON.fromJson(json, typeToken.getType());
    }

    /**
     * JSON反序列化（List）
     */
    public static <V> List<V> fromList(String json, Class<V> type) {
        TypeToken<List<V>> typeToken = (TypeToken<List<V>>)TypeToken.getParameterized(ArrayList.class, type);
        return GSON.fromJson(json, typeToken.getType());
    }

    /**
     * JSON反序列化（Map）
     */
    public static Map<String, Object> fromMap(String json) {
        return GSON.fromJson(json, new TypeToken<HashMap<String, Object>>() {}.getType());
    }

    /**
     * 宽松JSON反序列化
     */
    public static <V> V fromLenient(InputStream inputStream, Class<V> type) {
        JsonReader reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(inputStream)));
        reader.setLenient(true);
        return GSON.fromJson(reader, type);
    }

    /**
     * 宽松JSON反序列化（List）
     */
    public static <V> List<V> fromListLenient(InputStream inputStream, Class<V> type) {
        JsonReader reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(inputStream)));
        reader.setLenient(true);
        TypeToken<List<V>> typeToken = (TypeToken<List<V>>)TypeToken.getParameterized(ArrayList.class, type);
        return GSON.fromJson(reader, typeToken.getType());
    }

    /**
     * 宽松JSON反序列化
     */
    public static <V> V fromLenient(File file, Class<V> type) {
        try {
            JsonReader reader = new JsonReader(new FileReader(file));
            reader.setLenient(true);
            return GSON.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            throw new GsonException("gson lenient from error, file path: {}, type: {}", file.getPath(), type, e);
        }
    }

    /**
     * 宽松JSON反序列化（List）
     */
    public static <V> List<V> fromListLenient(File file, Class<V> type) {
        try {
            JsonReader reader = new JsonReader(new FileReader(file));
            reader.setLenient(true);
            TypeToken<List<V>> typeToken = (TypeToken<List<V>>)TypeToken.getParameterized(ArrayList.class, type);
            return GSON.fromJson(reader, typeToken.getType());
        } catch (FileNotFoundException e) {
            throw new GsonException("gson lenient from error, file path: {}, type: {}", file.getPath(), type, e);
        }
    }

    /**
     * 宽松JSON反序列化
     */
    public static <V> V fromLenient(String json, Class<V> type) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        return GSON.fromJson(reader, type);
    }

    /**
     * 宽松JSON反序列化
     */
    public static <V> V fromLenient(String json, Type type) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        return GSON.fromJson(reader, type);
    }

    /**
     * 宽松JSON反序列化
     */
    public static <V> V fromLenient(String json, TypeToken<V> typeToken) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        return GSON.fromJson(reader, typeToken.getType());
    }

    /**
     * 宽松JSON反序列化（List）
     */
    public static <V> List<V> fromListLenient(String json, Class<V> type) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        TypeToken<List<V>> typeToken = (TypeToken<List<V>>)TypeToken.getParameterized(ArrayList.class, type);
        return GSON.fromJson(reader, typeToken.getType());
    }

    /**
     * 序列化为JSON
     */
    public static <V> String to(List<V> list) {
        return GSON.toJson(list);
    }

    /**
     * 序列化为JSON
     */
    public static <V> String to(V v) {
        return GSON.toJson(v);
    }

    /**
     * 序列化为JSON文件
     */
    public static <V> void toFile(String path, List<V> list) {
        try (JsonWriter jsonWriter = new JsonWriter(new FileWriter(new File(path), true));) {
            GSON.toJson(list, new TypeToken<List<V>>() {}.getType(), jsonWriter);
            jsonWriter.flush();
        } catch (Exception e) {
            throw new GsonException("gson to file error, path: {}, list: {}", path, list, e);
        }
    }

    /**
     * 序列化为JSON文件
     */
    public static <V> void toFile(String path, V v) {
        try (JsonWriter jsonWriter = new JsonWriter(new FileWriter(new File(path), true));) {
            GSON.toJson(v, v.getClass(), jsonWriter);
            jsonWriter.flush();
        } catch (Exception e) {
            throw new GsonException("gson to file error, path: {}, obj: {}", path, v, e);
        }
    }

    /**
     * 从json串中获取某个字段
     * @return String，默认为 null
     */
    public static String getAsString(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        String propertyValue;
        JsonElement jsonByKey = getAsJsonObject(json, key);
        if (null == jsonByKey) {
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
     * @return int，默认为 0
     */
    public static int getAsInt(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return 0;
        }
        JsonElement jsonByKey = getAsJsonObject(json, key);
        if (null == jsonByKey) {
            return 0;
        }
        try {
            return jsonByKey.getAsInt();
        } catch (Exception e) {
            throw new GsonException("gson get int error, json: {}, key: {}", json, key, e);
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
        JsonElement jsonByKey = getAsJsonObject(json, key);
        if (null == jsonByKey) {
            return 0L;
        }
        try {
            return jsonByKey.getAsLong();
        } catch (Exception e) {
            throw new GsonException("gson get long error, json: {}, key: {}", json, key, e);
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
        JsonElement jsonByKey = getAsJsonObject(json, key);
        if (null == jsonByKey) {
            return 0.0;
        }
        try {
            return jsonByKey.getAsDouble();
        } catch (Exception e) {
            throw new GsonException("gson get double error, json: {}, key: {}", json, key, e);
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
        JsonElement jsonByKey = getAsJsonObject(json, key);
        if (null == jsonByKey) {
            return new BigInteger(String.valueOf(0.00));
        }
        try {
            return jsonByKey.getAsBigInteger();
        } catch (Exception e) {
            throw new GsonException("gson get big integer error, json: {}, key: {}", json, key, e);
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
        JsonElement jsonByKey = getAsJsonObject(json, key);
        if (null == jsonByKey) {
            return new BigDecimal("0.0");
        }
        try {
            return jsonByKey.getAsBigDecimal();
        } catch (Exception e) {
            throw new GsonException("gson get big decimal error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * 从json串中获取某个字段
     * @return boolean, 默认为 false
     */
    public static boolean getAsBoolean(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return false;
        }
        JsonPrimitive jsonByKey = (JsonPrimitive)getAsJsonObject(json, key);
        if (null == jsonByKey) {
            return false;
        }
        try {
            if (jsonByKey.isBoolean()) {
                return jsonByKey.getAsBoolean();
            } else {
                if (jsonByKey.isString()) {
                    String string = jsonByKey.getAsString();
                    if ("1".equals(string)) {
                        return true;
                    } else {
                        return BooleanUtils.toBoolean(string);
                    }
                } else {//number
                    return BooleanUtils.toBoolean(jsonByKey.getAsInt());
                }
            }
        } catch (Exception e) {
            throw new GsonException("gson get boolean error, json: {}, key: {}", json, key, e);
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
        JsonElement jsonByKey = getAsJsonObject(json, key);
        if (null == jsonByKey) {
            return 0;
        }
        try {
            return jsonByKey.getAsByte();
        } catch (Exception e) {
            throw new GsonException("gson get byte error, json: {}, key: {}", json, key, e);
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
        JsonElement jsonByKey = getAsJsonObject(json, key);
        if (null == jsonByKey) {
            return null;
        }
        try {
            return from(jsonByKey.getAsString(), type);
        } catch (Exception e) {
            throw new GsonException("gson get list error, json: {}, key: {}, type: {}", json, key, type, e);
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
        JsonElement jsonByKey = getAsJsonObject(json, key);
        if (null == jsonByKey) {
            return null;
        }
        try {
            JsonArray jsonArray = jsonByKey.getAsJsonArray();
            TypeToken<List<V>> typeToken = (TypeToken<List<V>>)TypeToken.getParameterized(ArrayList.class, type);
            return from(jsonArray.toString(), typeToken);
        } catch (Exception e) {
            throw new GsonException("gson get list error, json: {}, key: {}, type: {}", json, key, type, e);
        }
    }

    /**
     * 从json串中获取某个字段
     */
    public static JsonElement getAsJsonObject(String json, String key) {
        try {
            JsonElement element = JsonParser.parseString(json);
            JsonObject jsonObj = element.getAsJsonObject();
            return jsonObj.get(key);
        } catch (JsonSyntaxException e) {
            throw new GsonException("gson get object from json error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * 向json中添加属性
     * @return json
     */
    public static <V> String add(String json, String key, V value) {
        JsonElement element = JsonParser.parseString(json);
        JsonObject jsonObject = element.getAsJsonObject();
        add(jsonObject, key, value);
        return jsonObject.toString();
    }

    /**
     * 向json中添加属性
     */
    private static <V> void add(JsonObject jsonObject, String key, V value) {
        if (value instanceof String) {
            jsonObject.addProperty(key, (String)value);
        } else if (value instanceof Number) {
            jsonObject.addProperty(key, (Number)value);
        } else {
            jsonObject.addProperty(key, to(value));
        }
    }

    /**
     * 除去json中的某个属性
     * @return json
     */
    public static String remove(String json, String key) {
        JsonElement element = JsonParser.parseString(json);
        if (element.isJsonObject()) {
            JsonObject jsonObj = element.getAsJsonObject();
            jsonObj.remove(key);
        }
        return element.toString();
    }

    /**
     * 修改json中的属性
     */
    public static <V> String update(String json, String key, V value) {
        JsonElement element = JsonParser.parseString(json);
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
        JsonElement jsonElement = JsonParser.parseString(json);
        return GSON_PRETTY.toJson(jsonElement);
    }

    /**
     * 判断字符串是否是json
     * @return json
     */
    public static boolean isJson(String json) {
        try {
            return JsonParser.parseString(json).isJsonObject();
        } catch (Exception e) {
            return false;
        }
    }

}