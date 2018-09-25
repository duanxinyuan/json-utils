package com.dxy.library.json.jackson;

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
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsParser;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Jackson工具类
 * 优势：解析场景支持最完善，API最完善，可定制性最强，数据量大的时候，速度和Fastjson相差很小
 * @author duanxinyuan
 * 2018/6/28 23:24
 */
@Slf4j
public class JacksonUtil {
    private static ObjectMapper mapper;
    private static YAMLMapper yamlMapper;
    private static JavaPropsMapper javaPropsMapper;
    private static CsvMapper csvMapper;
    private static XmlMapper xmlMapper;

    static {
        mapper = new ObjectMapper();
        yamlMapper = new YAMLMapper();
        javaPropsMapper = new JavaPropsMapper();
        csvMapper = new CsvMapper();
        xmlMapper = new XmlMapper();
        initMapper(mapper);
        initMapper(yamlMapper);
        initMapper(javaPropsMapper);
        initMapper(csvMapper);
        initMapper(xmlMapper);

        //允许注释
        javaPropsMapper.enable(JavaPropsParser.Feature.ALLOW_COMMENTS);
        javaPropsMapper.enable(JavaPropsParser.Feature.ALLOW_YAML_COMMENTS);

        //去掉头尾空格
        csvMapper.enable(CsvParser.Feature.TRIM_SPACES);
        //忽略空行
        csvMapper.enable(CsvParser.Feature.SKIP_EMPTY_LINES);
        csvMapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
    }

    private static void initMapper(ObjectMapper objectMapper) {
        //为null的属性值不映射
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //为空字符串的属性值不映射
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        //为默认值的属性值不映射
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);
        //为null的属性值不映射
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //序列化BigDecimal时之间输出原始数字还是科学计数, 默认false, 即是否以toPlainString()科学计数方式来输出
        objectMapper.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        //允许将JSON空字符串强制转换为null对象值
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        //允许单个数值当做数组处理
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        //禁止重复键, 抛出异常
        objectMapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
        //禁止使用int代表Enum的order()來反序列化Enum, 抛出异常
        objectMapper.enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
        //有属性不能映射的时候不报错
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //使用null表示集合类型字段是时不抛异常
        objectMapper.disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
        //对象为空时不抛异常
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        //允许在JSON中使用c/c++风格注释
        objectMapper.enable(JsonParser.Feature.ALLOW_COMMENTS);
        //强制转义非ascii字符
        objectMapper.disable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
        //在JSON中允许未知字段名
        objectMapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        //时间格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        //识别Java8时间
        objectMapper.registerModule(new ParameterNamesModule());
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        //识别单引号
        objectMapper.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        //识别Guava类
        objectMapper.registerModule(new GuavaModule());
    }

    /**
     * 设置是否开启JSON格式美化
     * @param isEnable 为true表示开启
     */
    public static void setIndentOutputEnable(boolean isEnable) {
        if (isEnable) {
            //是否缩放排列输出, 默认false, 有些场合为了便于排版阅读则需要对输出做缩放排列
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
        } else {
            mapper.disable(SerializationFeature.INDENT_OUTPUT);
        }
    }


    /**
     * JSON解析
     */
    public static <V> V from(Object jsonObj, Class<V> c) {
        try {
            return mapper.readValue(jsonObj.toString(), c);
        } catch (IOException e) {
            log.error("jackson from error, json: {}, class: {}", jsonObj.toString(), c, e);
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
            log.error("jackson from error, json: {}, class: {}", json, c, e);
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
            log.error("jackson from error, json: {}, type: {}", json, type, e);
            return null;
        }
    }

    /**
     * 解析Recources目录下的Yaml文件
     * @param name 文件名
     */
    public static <V> V fromYamlRecource(String name, Class<V> c) {
        try (InputStream inputStream = getResourceStream(name); InputStreamReader reader = getResourceReader(inputStream)) {
            if (reader == null) {
                return null;
            }
            return yamlMapper.readValue(reader, c);
        } catch (IOException e) {
            log.error("jackson from yaml recource error, name: {}, class: {}", name, c, e);
            return null;
        }
    }

    /**
     * 解析Recources目录下的Yaml文件
     * @param name 文件名
     */
    public static <V> V fromYamlRecource(String name, TypeReference<V> type) {
        try (InputStream inputStream = getResourceStream(name); InputStreamReader reader = getResourceReader(inputStream)) {
            if (reader == null) {
                return null;
            }
            return yamlMapper.readValue(reader, type);
        } catch (IOException e) {
            log.error("jackson from yaml recource error, name: {}, type: {}", name, type, e);
            return null;
        }
    }

    /**
     * 解析Yaml文件
     * @param path 文件路径
     */
    public static <V> V fromYamlFile(String path, Class<V> c) {
        try {
            return yamlMapper.readValue(new File(path), c);
        } catch (IOException e) {
            log.error("jackson from yaml error, path: {}, class: {}", path, c, e);
            return null;
        }
    }

    /**
     * 解析Yaml文件
     * @param path 文件路径
     */
    public static <V> V fromYamlFile(String path, TypeReference<V> type) {
        try {
            return yamlMapper.readValue(new File(path), type);
        } catch (IOException e) {
            log.error("jackson from yaml error, path: {}, type: {}", path, type, e);
            return null;
        }
    }

    /**
     * 解析Recources目录下的Properties文件
     * @param name 文件名
     */
    public static <V> V fromPropRecource(String name, Class<V> c) {
        try (InputStream inputStream = getResourceStream(name); InputStreamReader reader = getResourceReader(inputStream)) {
            if (reader == null) {
                return null;
            }
            return javaPropsMapper.readValue(reader, c);
        } catch (IOException e) {
            log.error("jackson from properties recource error, name: {}, class: {}", name, c, e);
            return null;
        }
    }

    /**
     * 解析Recources目录下的Properties文件
     * @param name 文件名
     */
    public static <V> V fromPropRecource(String name, TypeReference<V> type) {
        try (InputStream inputStream = getResourceStream(name); InputStreamReader reader = getResourceReader(inputStream)) {
            if (reader == null) {
                return null;
            }
            return javaPropsMapper.readValue(reader, type);
        } catch (IOException e) {
            log.error("jackson from properties recource error, name: {}, type: {}", name, type, e);
            return null;
        }
    }

    /**
     * 解析Recources目录下的Csv文件（受限于CSV的格式，Jackson不支持深层次结构的CSV解析，不支持嵌套类）
     * @param name 文件名
     * @param separator cloumn的分隔符
     */
    public static <V> List<V> fromCsvRecource(String name, String separator, Class<V> c) {
        try (InputStream inputStream = getResourceStream(name); InputStreamReader reader = getResourceReader(inputStream)) {
            if (reader == null) {
                return null;
            }
            CsvSchema schema = CsvSchema.builder().setColumnSeparator(separator.charAt(0)).setUseHeader(true).build();
            return (List<V>) csvMapper.reader(schema).forType(c).readValues(reader).readAll();
        } catch (IOException e) {
            log.error("jackson from csv recource error, name: {}, class: {}", name, c, e);
            return null;
        }
    }

    /**
     * 解析Csv文件（受限于CSV的格式，Jackson不支持深层次结构的CSV解析，不支持嵌套类）
     * @param path 文件路径
     */
    public static <V> List<V> fromCsvFile(String path, String separator, Class<V> c) {
        try {
            CsvSchema schema = CsvSchema.builder().setColumnSeparator(separator.charAt(0)).setUseHeader(true).build();
            return (List<V>) csvMapper.reader(schema).forType(c).readValues(new File(path)).readAll();
        } catch (IOException e) {
            log.error("jackson from csv error, path: {}, class: {}", path, c, e);
            return null;
        }
    }


    /**
     * 解析Recources目录下的Xml文件
     * @param name 文件名
     */
    public static <V> V fromXmlRecource(String name, Class<V> c) {
        try (InputStream inputStream = getResourceStream(name); InputStreamReader reader = getResourceReader(inputStream)) {
            if (reader == null) {
                return null;
            }
            return xmlMapper.readValue(reader, c);
        } catch (IOException e) {
            log.error("jackson from xml recource error, name: {}, class: {}", name, c, e);
            return null;
        }
    }

    /**
     * 解析Recources目录下的Xml文件
     * @param name 文件名
     */
    public static <V> V fromXmlRecource(String name, TypeReference<V> type) {
        try (InputStream inputStream = getResourceStream(name); InputStreamReader reader = getResourceReader(inputStream)) {
            if (reader == null) {
                return null;
            }
            return xmlMapper.readValue(reader, type);
        } catch (IOException e) {
            log.error("jackson from xml recource error, name: {}, type: {}", name, type, e);
            return null;
        }
    }

    /**
     * 解析Xml文件
     * @param path 文件路径
     */
    public static <V> V fromXmlFile(String path, Class<V> c) {
        try {
            return xmlMapper.readValue(new File(path), c);
        } catch (IOException e) {
            log.error("jackson from xml error, path: {}, class: {}", path, c, e);
            return null;
        }
    }

    /**
     * 解析Xml文件
     * @param path 文件路径
     */
    public static <V> V fromXmlFile(String path, TypeReference<V> type) {
        try {
            return xmlMapper.readValue(new File(path), type);
        } catch (IOException e) {
            log.error("jackson from xml error, path: {}, type: {}", path, type, e);
            return null;
        }
    }

    /**
     * 解析Xml字符串
     */
    public static <V> V fromXml(String xml, Class<V> c) {
        try {
            return xmlMapper.readValue(xml, c);
        } catch (IOException e) {
            log.error("jackson from xml error, xml: {}, class: {}", xml, c, e);
            return null;
        }
    }

    /**
     * 解析Xml字符串
     */
    public static <V> V fromXml(String xml, TypeReference<V> type) {
        try {
            return xmlMapper.readValue(xml, type);
        } catch (IOException e) {
            log.error("jackson from xml error, xml: {}, type: {}", xml, type, e);
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
            log.error("jackson to error, obj: {}", list, e);
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
            log.error("jackson to error, obj: {}", v, e);
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
            log.error("jackson get string error, json: {}, key: {}", json, key, e);
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
            log.error("jackson get int error, json: {}, key: {}", json, key, e);
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
            log.error("jackson get long error, json: {}, key: {}", json, key, e);
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
            log.error("jackson get double error, json: {}, key: {}", json, key, e);
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
            log.error("jackson get biginteger error, json: {}, key: {}", json, key, e);
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
            log.error("jackson get bigdecimal error, json: {}, key: {}", json, key, e);
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
        try {
            JsonNode node = mapper.readTree(json);
            if (null != node) {
                return node.get(key).booleanValue();
            } else {
                return false;
            }
        } catch (IOException e) {
            log.error("jackson get boolean error, json: {}, key: {}", json, key, e);
            return false;
        }
    }

    /**
     * 从json串中获取某个字段
     * @return boolean, 默认为false
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
            log.error("jackson get byte error, json: {}, key: {}", json, key, e);
            return null;
        }
    }

    /**
     * 从json串中获取某个字段
     * @return boolean, 默认为false
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
            log.error("jackson add error, json: {}, key: {}, value: {}", json, key, value, e);
            return json;
        }
    }

    /**
     * 向json中添加属性
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
            log.error("jackson remove error, json: {}, key: {}", json, key, e);
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
            log.error("jackson update error, json: {}, key: {}, value: {}", json, key, value, e);
            return json;
        }
    }

    /**
     * 格式化Json(美化)
     * @return json
     */
    public static String format(String json) {
        try {
            JsonNode node = mapper.readTree(json);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        } catch (IOException e) {
            log.error("jackson format json error, json: {}", json, e);
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
            log.error("jackson check json error, json: {}", json, e);
            return false;
        }
    }

    private static InputStream getResourceStream(String name) {
        return JacksonUtil.class.getClassLoader().getResourceAsStream(name);
    }

    private static InputStreamReader getResourceReader(InputStream inputStream) {
        if (null == inputStream) {
            return null;
        }
        return new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    }
}
