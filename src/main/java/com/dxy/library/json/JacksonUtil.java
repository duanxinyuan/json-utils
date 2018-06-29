package com.dxy.library.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Jackson工具类
 * @author duanxinyuan
 * 2018/6/28 23:24
 */
@Slf4j
public class JacksonUtil {
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        //为null的属性值不映射
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //为空字符串的属性值不映射
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        //为默认值的属性值不映射
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);
        //为null的属性值不映射
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //序列化BigDecimal时之间输出原始数字还是科学计数，默认false，即是否以toPlainString()科学计数方式来输出
        mapper.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        //允许将JSON空字符串强制转换为null对象值
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        //允许单个数值当做数组处理
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        //禁止重复键，抛出异常
        mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
        //禁止使用int代表Enum的order()來反序列化Enum，抛出异常
        mapper.enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
        //有属性不能映射的时候不报错
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //使用null表示集合类型字段是时不抛异常
        mapper.disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
        //对象为空时不抛异常
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        //是否缩放排列输出，默认false，有些场合为了便于排版阅读则需要对输出做缩放排列
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        //允许在JSON中使用c/c++风格注释
        mapper.enable(JsonParser.Feature.ALLOW_COMMENTS);
        //强制转义非ascii字符
        mapper.disable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
        //在JSON中允许未知字段名
        mapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        //时间格式
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        //识别Java8时间
        mapper.registerModule(new ParameterNamesModule());
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        //识别单引号
        mapper.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        //识别Guava类
        mapper.registerModule(new GuavaModule());
    }

    /**
     * JSON解析
     */
    public static <V> V from(Object jsonObj, Class<V> c) {
        try {
            return mapper.readValue(jsonObj.toString(), c);
        } catch (IOException e) {
            log.info("JSON解析异常", e);
            return null;
        }
    }

    /**
     * JSON解析
     */
    public static <V> V from(String json, Class<V> c) {
        try {
            return mapper.readValue(json, c);
        } catch (IOException e) {
            log.info("JSON解析异常", e);
            return null;
        }
    }

    /**
     * JSON解析
     */
    public static <V> V from(String json, TypeReference<V> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            log.info("JSON解析异常", e);
            return null;
        }
    }

    /**
     * 转化为JSON
     */
    public static <V> String to(ArrayList<V> list) {
        try {
            return mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            log.info("JSON解析异常", e);
            return null;
        }
    }

    /**
     * 转化为JSON
     */
    public static <V> String to(V v) {
        try {
            return mapper.writeValueAsString(v);
        } catch (JsonProcessingException e) {
            log.info("JSON解析异常", e);
            return null;
        }
    }

    /**
     * 从json串中获取某个字段
     * @return String
     */
    public static String getString(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            JsonNode node = mapper.readTree(json);
            if (null != node) {
                return node.get(key).toString();
            } else {
                return null;
            }
        } catch (IOException e) {
            log.info("JSON解析异常", e);
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
        try {
            JsonNode node = mapper.readTree(json);
            if (null != node) {
                return node.get(key).intValue();
            } else {
                return null;
            }
        } catch (IOException e) {
            log.info("JSON解析异常", e);
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
        try {
            JsonNode node = mapper.readTree(json);
            if (null != node) {
                return node.get(key).longValue();
            } else {
                return null;
            }
        } catch (IOException e) {
            log.info("JSON解析异常", e);
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
        try {
            JsonNode node = mapper.readTree(json);
            if (null != node) {
                return node.get(key).doubleValue();
            } else {
                return null;
            }
        } catch (IOException e) {
            log.info("JSON解析异常", e);
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
        try {
            JsonNode node = mapper.readTree(json);
            if (null != node) {
                return node.get(key).bigIntegerValue();
            } else {
                return null;
            }
        } catch (IOException e) {
            log.info("JSON解析异常", e);
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
        try {
            JsonNode node = mapper.readTree(json);
            if (null != node) {
                return node.get(key).decimalValue();
            } else {
                return null;
            }
        } catch (IOException e) {
            log.info("JSON解析异常", e);
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
        try {
            JsonNode node = mapper.readTree(json);
            if (null != node) {
                return node.get(key).booleanValue();
            } else {
                return false;
            }
        } catch (IOException e) {
            log.info("JSON解析异常", e);
            return false;
        }
    }

    /**
     * 从json串中获取某个字段
     * @return boolean，默认为false
     */
    public static byte[] getByte(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            JsonNode node = mapper.readTree(json);
            if (null != node) {
                return node.get(key).binaryValue();
            } else {
                return null;
            }
        } catch (IOException e) {
            log.info("JSON解析异常", e);
            return null;
        }
    }

    /**
     * 从json串中获取某个字段
     * @return boolean，默认为false
     */
    public static <T> ArrayList<T> getList(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        String string = getString(json, key);
        return from(string, new TypeReference<ArrayList<T>>() {});
    }

    /**
     * 向json中添加属性
     * @return json
     */
    public static <T> String add(String json, String key, T value) {
        try {
            JsonNode node = mapper.readTree(json);
            add(node, key, value);
            return node.toString();
        } catch (IOException e) {
            log.info("JSON解析异常", e);
            return json;
        }
    }

    /**
     * 向json中添加属性
     * @return json
     */
    private static <T> void add(JsonNode jsonNode, String key, T value) {
        if (value instanceof String) {
            ((ObjectNode) jsonNode).put(key, (String) value);
        } else if (value instanceof Short) {
            ((ObjectNode) jsonNode).put(key, (Short) value);
        } else if (value instanceof Integer) {
            ((ObjectNode) jsonNode).put(key, (Integer) value);
        } else if (value instanceof Long) {
            ((ObjectNode) jsonNode).put(key, (Long) value);
        } else if (value instanceof Float) {
            ((ObjectNode) jsonNode).put(key, (Float) value);
        } else if (value instanceof Double) {
            ((ObjectNode) jsonNode).put(key, (Double) value);
        } else if (value instanceof BigDecimal) {
            ((ObjectNode) jsonNode).put(key, (BigDecimal) value);
        } else if (value instanceof BigInteger) {
            ((ObjectNode) jsonNode).put(key, (BigInteger) value);
        } else if (value instanceof Boolean) {
            ((ObjectNode) jsonNode).put(key, (Boolean) value);
        } else if (value instanceof byte[]) {
            ((ObjectNode) jsonNode).put(key, (byte[]) value);
        } else {
            ((ObjectNode) jsonNode).put(key, to(value));
        }
    }

    /**
     * 除去json中的某个属性
     * @return json
     */
    public static String remove(String json, String key) {
        try {
            JsonNode node = mapper.readTree(json);
            ((ObjectNode) node).remove(key);
            return node.toString();
        } catch (IOException e) {
            log.info("JSON解析异常", e);
            return json;
        }
    }

    /**
     * 修改json中的属性
     */
    public static <T> String update(String json, String key, T value) {
        try {
            JsonNode node = mapper.readTree(json);
            ((ObjectNode) node).remove(key);
            add(node, key, value);
            return node.toString();
        } catch (IOException e) {
            log.info("JSON解析异常", e);
            return json;
        }
    }

    /**
     * 判断字符串是否是json
     * @return json
     */
    public static boolean isJson(String json) {
        try {
            mapper.readTree(json);
            return true;
        } catch (Exception e) {
            log.error("判断字符串是否是json失败，Json内容：{}", json, e);
            return false;
        }
    }
}
