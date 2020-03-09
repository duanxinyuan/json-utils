package com.dxy.library.json.jackson.extend;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsParser;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.dxy.library.json.jackson.JacksonUtil;
import com.dxy.library.json.jackson.exception.JacksonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Jackson扩展工具类，支持Properties、YAML、CSV、XML读写
 * @author duanxinyuan
 * 2020/1/13 21:12
 */
@Slf4j
public class JacksonExtendUtil extends JacksonUtil {

    private static YAMLMapper yamlMapper;
    private static JavaPropsMapper propsMapper;
    private static CsvMapper csvMapper;
    private static XmlMapper xmlMapper;

    /**
     * CSV默认分隔符
     */
    private static final String CSV_DEFAULT_COLUMN_SEPARATOR = ",";

    static {
        try {
            //初始化
            initMapper();
            //配置序列化级别
            configPropertyInclusion();
            //配置JSON缩进支持
            configIndentOutput();
            //配置普通属性
            configCommon();
            //配置特殊属性
            configSpecial();
        } catch (Exception e) {
            log.error("jackson config error", e);
        }
    }

    private static void initMapper() {
        yamlMapper = new YAMLMapper();
        propsMapper = new JavaPropsMapper();
        csvMapper = new CsvMapper();
        xmlMapper = new XmlMapper();
    }

    private static void configCommon() {
        config(yamlMapper);
        config(propsMapper);
        config(csvMapper);
        config(xmlMapper);
    }

    private static void configPropertyInclusion() {
        yamlMapper.setSerializationInclusion(DEFAULT_PROPERTY_INCLUSION);
        propsMapper.setSerializationInclusion(DEFAULT_PROPERTY_INCLUSION);
        csvMapper.setSerializationInclusion(DEFAULT_PROPERTY_INCLUSION);
        xmlMapper.setSerializationInclusion(DEFAULT_PROPERTY_INCLUSION);
    }

    private static void configIndentOutput() {
        yamlMapper.configure(SerializationFeature.INDENT_OUTPUT, IS_ENABLE_INDENT_OUTPUT);
        propsMapper.configure(SerializationFeature.INDENT_OUTPUT, IS_ENABLE_INDENT_OUTPUT);
        csvMapper.configure(SerializationFeature.INDENT_OUTPUT, IS_ENABLE_INDENT_OUTPUT);
        xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, IS_ENABLE_INDENT_OUTPUT);
    }

    private static void configSpecial() {
        //使用系统换行符
        yamlMapper.enable(YAMLGenerator.Feature.USE_PLATFORM_LINE_BREAKS);
        //允许注释
        yamlMapper.enable(JsonParser.Feature.ALLOW_COMMENTS);
        yamlMapper.enable(JsonParser.Feature.ALLOW_YAML_COMMENTS);
        //允许注释
        propsMapper.enable(JavaPropsParser.Feature.ALLOW_COMMENTS);
        propsMapper.enable(JavaPropsParser.Feature.ALLOW_YAML_COMMENTS);
        //去掉头尾空格
        csvMapper.enable(CsvParser.Feature.TRIM_SPACES);
        //忽略空行
        csvMapper.enable(CsvParser.Feature.SKIP_EMPTY_LINES);
        csvMapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
    }


    /**
     * 反序列化Resources目录下的Yaml文件
     * @param name 文件名
     */
    public static <V> V fromYamlResource(String name, Class<V> type) {
        return fromYamlResource(name, (Type) type);
    }

    /**
     * 反序列化Resources目录下的Yaml文件
     * @param name 文件名
     */
    public static <V> V fromYamlResource(String name, TypeReference<V> type) {
        return fromYamlResource(name, type.getType());
    }

    /**
     * 反序列化Resources目录下的Yaml文件
     * @param name 文件名
     */
    public static <V> V fromYamlResource(String name, Type type) {
        try (InputStream inputStream = getResourceStream(name); InputStreamReader reader = getResourceReader(inputStream)) {
            if (reader == null) {
                return null;
            }
            JavaType javaType = yamlMapper.getTypeFactory().constructType(type);
            return yamlMapper.readValue(reader, javaType);
        } catch (IOException e) {
            log.error("jackson from yaml resource error, name: {}, type: {}", name, type, e);
            throw new JacksonException("jackson from yaml resource error, name: {}, type: {}", name, type, e);
        }
    }

    /**
     * 反序列化Yaml文件
     * @param path 文件路径
     */
    public static <V> V fromYamlFile(String path, Class<V> type) {
        return fromYamlFile(path, (Type) type);
    }

    /**
     * 反序列化Yaml文件
     * @param path 文件路径
     */
    public static <V> V fromYamlFile(String path, TypeReference<V> type) {
        return fromYamlFile(path, type.getType());
    }

    /**
     * 反序列化Yaml文件
     * @param path 文件路径
     */
    public static <V> V fromYamlFile(String path, Type type) {
        try {
            JavaType javaType = yamlMapper.getTypeFactory().constructType(type);
            return yamlMapper.readValue(new File(path), javaType);
        } catch (IOException e) {
            log.error("jackson from yaml file error, path: {}, type: {}", path, type, e);
            throw new JacksonException("jackson from yaml file error, path: {}, type: {}", path, type, e);
        }
    }

    /**
     * 反序列化Resources目录下的Properties文件
     * @param name 文件名
     */
    public static <V> V fromPropResource(String name, Class<V> type) {
        return fromPropResource(name, (Type) type);
    }

    /**
     * 反序列化Resources目录下的Properties文件
     * @param name 文件名
     */
    public static <V> V fromPropResource(String name, TypeReference<V> type) {
        return fromPropResource(name, type.getType());
    }

    /**
     * 反序列化Resources目录下的Properties文件
     * @param name 文件名
     */
    public static <V> V fromPropResource(String name, Type type) {
        try (InputStream inputStream = getResourceStream(name); InputStreamReader reader = getResourceReader(inputStream)) {
            if (reader == null) {
                return null;
            }
            JavaType javaType = propsMapper.getTypeFactory().constructType(type);
            return propsMapper.readValue(reader, javaType);
        } catch (IOException e) {
            log.error("jackson from properties resource error, name: {}, type: {}", name, type, e);
            throw new JacksonException("jackson from properties resource error, name: {}, type: {}", name, type, e);
        }
    }

    /**
     * 反序列化Resources目录下的Csv文件（受限于CSV的格式，Jackson不支持深层次结构的CSV反序列化，不支持嵌套类）
     * @param name 文件名
     */
    public static <V> List<V> fromCsvResource(String name, Class<V> type) {
        return fromCsvResource(name, CSV_DEFAULT_COLUMN_SEPARATOR, type);
    }

    /**
     * 反序列化Resources目录下的Csv文件（受限于CSV的格式，Jackson不支持深层次结构的CSV反序列化，不支持嵌套类）
     * @param name 文件名
     * @param separator 列分隔符
     */
    public static <V> List<V> fromCsvResource(String name, String separator, Class<V> type) {
        return fromCsvResource(name, separator, (Type) type);
    }

    /**
     * 反序列化Resources目录下的Csv文件（受限于CSV的格式，Jackson不支持深层次结构的CSV反序列化，不支持嵌套类）
     * @param name 文件名
     */
    public static <V> List<V> fromCsvResource(String name, TypeReference<V> type) {
        return fromCsvResource(name, CSV_DEFAULT_COLUMN_SEPARATOR, type.getType());
    }

    /**
     * 反序列化Resources目录下的Csv文件（受限于CSV的格式，Jackson不支持深层次结构的CSV反序列化，不支持嵌套类）
     * @param name 文件名
     * @param separator 列分隔符
     */
    public static <V> List<V> fromCsvResource(String name, String separator, TypeReference<V> type) {
        return fromCsvResource(name, separator, type.getType());
    }

    /**
     * 反序列化Resources目录下的Csv文件（受限于CSV的格式，Jackson不支持深层次结构的CSV反序列化，不支持嵌套类）
     * @param name 文件名
     * @param separator 列分隔符
     */
    public static <V> List<V> fromCsvResource(String name, String separator, Type type) {
        try (InputStream inputStream = getResourceStream(name); InputStreamReader reader = getResourceReader(inputStream)) {
            if (reader == null) {
                return null;
            }
            CsvSchema schema = CsvSchema.builder().setColumnSeparator(separator.charAt(0)).setUseHeader(true).build();
            JavaType javaType = csvMapper.getTypeFactory().constructType(type);
            return (List<V>) csvMapper.reader(schema).forType(javaType).readValues(reader).readAll();
        } catch (IOException e) {
            log.error("jackson from csv resource error, name: {}, type: {}", name, type, e);
            throw new JacksonException("jackson from csv resource error, name: {}, type: {}", name, type, e);
        }
    }

    /**
     * 反序列化Csv文件（受限于CSV的格式，Jackson不支持深层次结构的CSV反序列化，不支持嵌套类）
     * @param path 文件路径
     */
    public static <V> List<V> fromCsvFile(String path, Class<V> type) {
        return fromCsvFile(path, CSV_DEFAULT_COLUMN_SEPARATOR, type);
    }

    /**
     * 反序列化Csv文件（受限于CSV的格式，Jackson不支持深层次结构的CSV反序列化，不支持嵌套类）
     * @param separator 列分隔符
     * @param path 文件路径
     */
    public static <V> List<V> fromCsvFile(String path, String separator, Class<V> type) {
        return fromCsvFile(path, separator, (Type) type);
    }

    /**
     * 反序列化Csv文件（受限于CSV的格式，Jackson不支持深层次结构的CSV反序列化，不支持嵌套类）
     * @param path 文件路径
     */
    public static <V> List<V> fromCsvFile(String path, TypeReference<V> type) {
        return fromCsvFile(path, CSV_DEFAULT_COLUMN_SEPARATOR, type);
    }

    /**
     * 反序列化Csv文件（受限于CSV的格式，Jackson不支持深层次结构的CSV反序列化，不支持嵌套类）
     * @param separator 列分隔符
     * @param path 文件路径
     */
    public static <V> List<V> fromCsvFile(String path, String separator, TypeReference<V> type) {
        return fromCsvFile(path, separator, type.getType());
    }

    /**
     * 反序列化Csv文件（受限于CSV的格式，Jackson不支持深层次结构的CSV反序列化，不支持嵌套类）
     * @param separator 列分隔符
     * @param path 文件路径
     */
    public static <V> List<V> fromCsvFile(String path, String separator, Type type) {
        try {
            CsvSchema schema = CsvSchema.builder().setColumnSeparator(separator.charAt(0)).setUseHeader(true).build();
            JavaType javaType = csvMapper.getTypeFactory().constructType(type);
            return (List<V>) csvMapper.reader(schema).forType(javaType).readValues(new File(path)).readAll();
        } catch (IOException e) {
            log.error("jackson from csv file error, path: {}, type: {}", path, type, e);
            throw new JacksonException("jackson from csv file error, path: {}, type: {}", path, type, e);
        }
    }


    /**
     * 反序列化Resources目录下的Xml文件
     * @param name 文件名
     */
    public static <V> V fromXmlResource(String name, Class<V> type) {
        return fromXmlResource(name, (Type) type);

    }

    /**
     * 反序列化Resources目录下的Xml文件
     * @param name 文件名
     */
    public static <V> V fromXmlResource(String name, TypeReference<V> type) {
        return fromXmlResource(name, type.getType());
    }

    /**
     * 反序列化Resources目录下的Xml文件
     * @param name 文件名
     */
    public static <V> V fromXmlResource(String name, Type type) {
        try (InputStream inputStream = getResourceStream(name); InputStreamReader reader = getResourceReader(inputStream)) {
            if (reader == null) {
                return null;
            }
            JavaType javaType = xmlMapper.getTypeFactory().constructType(type);
            return xmlMapper.readValue(reader, javaType);
        } catch (IOException e) {
            log.error("jackson from xml resource error, name: {}, type: {}", name, type, e);
            throw new JacksonException("jackson from xml resource error, name: {}, type: {}", name, type, e);
        }
    }

    /**
     * 反序列化Xml文件
     * @param path 文件路径
     */
    public static <V> V fromXmlFile(String path, Class<V> type) {
        return fromXmlFile(path, (Type) type);
    }

    /**
     * 反序列化Xml文件
     * @param path 文件路径
     */
    public static <V> V fromXmlFile(String path, TypeReference<V> type) {
        return fromXmlFile(path, type.getType());
    }

    /**
     * 反序列化Xml文件
     * @param path 文件路径
     */
    public static <V> V fromXmlFile(String path, Type type) {
        try {
            JavaType javaType = xmlMapper.getTypeFactory().constructType(type);
            return xmlMapper.readValue(new File(path), javaType);
        } catch (IOException e) {
            log.error("jackson from xml file error, path: {}, type: {}", path, type, e);
            throw new JacksonException("jackson from xml file error, path: {}, type: {}", path, type, e);
        }
    }

    /**
     * 反序列化Xml字符串
     */
    public static <V> V fromXml(String xml, Class<V> type) {
        return fromXml(xml, (Type) type);
    }

    /**
     * 反序列化Xml字符串
     */
    public static <V> V fromXml(String xml, TypeReference<V> type) {
        return fromXml(xml, type.getType());
    }

    /**
     * 反序列化Xml字符串
     */
    public static <V> V fromXml(String xml, Type type) {
        if (xml == null) {
            return null;
        }
        try {
            JavaType javaType = xmlMapper.getTypeFactory().constructType(type);
            return xmlMapper.readValue(xml, javaType);
        } catch (IOException e) {
            log.error("jackson from xml error, xml: {}, type: {}", xml, type, e);
            throw new JacksonException("jackson from xml error, xml: {}, type: {}", xml, type, e);
        }
    }

    /**
     * 序列化为YAML
     */
    public static <V> String toYaml(V v) {
        try {
            return yamlMapper.writeValueAsString(v);
        } catch (JsonProcessingException e) {
            log.error("jackson to yaml error, data: {}", v, e);
            throw new JacksonException("jackson to yaml error, data: {}", v, e);
        }
    }

    /**
     * 序列化为YAML文件
     */
    public static <V> void toYamlFile(String path, V v) {
        try (Writer writer = new FileWriter(new File(path), true)) {
            yamlMapper.writeValue(writer, v);
        } catch (Exception e) {
            log.error("jackson to yaml file error, path: {}, data: {}", path, v, e);
            throw new JacksonException("jackson to yaml file error, path: {}, data: {}", path, v, e);
        }
    }

    /**
     * 序列化为Properties
     */
    public static <V> String toProp(V v) {
        try {
            String string = propsMapper.writeValueAsString(v);
            return StringEscapeUtils.unescapeJava(string);
        } catch (JsonProcessingException e) {
            log.error("jackson to properties error, data: {}", v, e);
            throw new JacksonException("jackson to properties error, data: {}", v, e);
        }
    }

    /**
     * 序列化为Properties文件
     */
    public static <V> void toPropFile(String path, V v) {
        try (Writer writer = new FileWriter(new File(path), true)) {
            JavaPropsSchema schema = JavaPropsSchema.emptySchema();
            propsMapper.writer(schema).writeValues(writer).write(v);
        } catch (Exception e) {
            log.error("jackson to properties file error, path: {}, data: {}", path, v, e);
            throw new JacksonException("jackson to properties file error, path: {}, data: {}", path, v, e);
        }
    }

    /**
     * 序列化为CSV
     */
    public static <V> String toCsv(List<V> list) {
        return toCsv(CSV_DEFAULT_COLUMN_SEPARATOR, list);
    }

    /**
     * 序列化为CSV
     */
    public static <V> String toCsv(String separator, List<V> list) {
        try {
            Class type = list.get(0).getClass();
            CsvSchema schema = csvMapper.schemaFor(type).withHeader().withColumnSeparator(separator.charAt(0));
            return csvMapper.writer(schema).writeValueAsString(list);
        } catch (JsonProcessingException e) {
            log.error("jackson to csv error, data: {}", list, e);
            throw new JacksonException("jackson to csv error, data: {}", list, e);
        }
    }

    /**
     * 序列化为CSV（序列化的对象不能嵌套对象）
     */
    public static <V> String toCsv(V v) {
        return toCsv(CSV_DEFAULT_COLUMN_SEPARATOR, v);
    }

    /**
     * 序列化为CSV（序列化的对象不能嵌套对象）
     */
    public static <V> String toCsv(String separator, V v) {
        try {
            CsvSchema schema = csvMapper.schemaFor(v.getClass()).withHeader().withColumnSeparator(separator.charAt(0));
            return csvMapper.writer(schema).writeValueAsString(v);
        } catch (JsonProcessingException e) {
            log.error("jackson to csv error, data: {}", v, e);
            throw new JacksonException("jackson to csv error, data: {}", v, e);
        }
    }

    /**
     * 序列化为CSV文件（序列化的对象不能嵌套对象）
     */
    public static <V> void toCsvFile(String path, List<V> list) {
        toCsvFile(path, CSV_DEFAULT_COLUMN_SEPARATOR, list);
    }

    /**
     * 序列化为CSV文件（序列化的对象不能嵌套对象）
     */
    public static <V> void toCsvFile(String path, String separator, List<V> list) {
        try (Writer writer = new FileWriter(new File(path), true)) {
            Class type = list.get(0).getClass();
            CsvSchema schema = csvMapper.schemaFor(type).withHeader().withColumnSeparator(separator.charAt(0));
            csvMapper.writer(schema).writeValues(writer).writeAll(list);
        } catch (Exception e) {
            log.error("jackson to csv file error, path: {}, separator: {}, list: {}", path, separator, list, e);
            throw new JacksonException("jackson to csv file error, path: {}, separator: {}, list: {}", path, separator, list, e);
        }
    }

    /**
     * 序列化为CSV文件（序列化的对象不能嵌套对象）
     */
    public static <V> void toCsvFile(String path, V v) {
        toCsvFile(path, CSV_DEFAULT_COLUMN_SEPARATOR, v);
    }

    /**
     * 序列化为CSV文件（序列化的对象不能嵌套对象）
     */
    public static <V> void toCsvFile(String path, String separator, V v) {
        try (Writer writer = new FileWriter(new File(path), true)) {
            CsvSchema schema = csvMapper.schemaFor(v.getClass()).withHeader().withColumnSeparator(separator.charAt(0));
            csvMapper.writer(schema).writeValues(writer).write(v);
        } catch (Exception e) {
            log.error("jackson to csv file error, path: {}, separator: {}, data: {}", path, separator, v, e);
            throw new JacksonException("jackson to csv file error, path: {}, separator: {}, data: {}", path, separator, v, e);
        }
    }

    /**
     * 序列化为XML
     */
    public static <V> String toXml(V v) {
        return toXml(v, true);
    }

    /**
     * 序列化为XML
     * @param isIndent 是否美化
     */
    public static <V> String toXml(V v, boolean isIndent) {
        try {
            if (isIndent) {
                return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(v);
            } else {
                return xmlMapper.writeValueAsString(v);
            }
        } catch (JsonProcessingException e) {
            log.error("jackson to xml error, data: {}, isIndent, {}", v, isIndent, e);
            throw new JacksonException("jackson to xml error, data: {}, isIndent, {}", v, isIndent, e);
        }
    }

    /**
     * 序列化为XML文件
     */
    public static <V> void toXmlFile(String path, V v) {
        toXmlFile(path, v, true);
    }

    /**
     * 序列化为XML文件
     */
    public static <V> void toXmlFile(String path, V v, boolean isIndent) {
        try (Writer writer = new FileWriter(new File(path), true)) {
            if (isIndent) {
                xmlMapper.writerWithDefaultPrettyPrinter().writeValue(writer, v);
            } else {
                xmlMapper.writeValue(writer, v);
            }
        } catch (Exception e) {
            log.error("jackson to xml file error, path: {}, data: {}, isIndent: {}", path, v, isIndent, e);
            throw new JacksonException("jackson to xml file error, path: {}, data: {}, isIndent: {}", path, v, isIndent, e);
        }
    }

    /**
     * 加载配置文件
     * @param name 配置文件名称（支持properties、yaml、yml、xml四种格式的文件）
     */
    public static Map<String, Object> loadPropertiesAsMap(String name) {
        name = name.trim();
        HashMap<String, Object> hashMap;
        if (name.endsWith("properties")) {
            hashMap = fromPropResource(name, new TypeReference<HashMap<String, Object>>() {});
        } else if (name.endsWith("yaml") || name.endsWith("yml")) {
            hashMap = fromYamlResource(name, new TypeReference<HashMap<String, Object>>() {});
        } else if (name.endsWith("xml")) {
            hashMap = fromXmlResource(name, new TypeReference<HashMap<String, Object>>() {});
        } else {
            throw new JacksonException("unsupported file format");
        }
        if (hashMap != null && hashMap.size() > 0) {
            hashMap = recursion(hashMap);
        }
        return hashMap;
    }

    /**
     * 递归将带有等级的Key转换成Map
     */
    private static HashMap<String, Object> recursion(HashMap<String, Object> prop) {
        HashMap<String, Object> result = Maps.newHashMap();
        List<String> removeKeys = Lists.newArrayList();
        prop.forEach((k, v) -> {
            if (null != v && ClassUtils.isAssignable(v.getClass(), Map.class)) {
                HashMap<String, Object> from = JacksonUtil.from(JacksonUtil.to(v), new TypeReference<HashMap<String, Object>>() {});
                result.putAll(recursion(k, result, from));
                if (!prop.containsKey(k)) {
                    removeKeys.add(k);
                }
            } else {
                result.put(k, v);
            }
        });
        for (String removeKey : removeKeys) {
            prop.remove(removeKey);
        }
        return result;
    }

    private static HashMap<String, Object> recursion(String previousKey, HashMap<String, Object> result, HashMap<String, Object> prop) {
        for (Map.Entry<String, Object> entry : prop.entrySet()) {
            String key = entry.getKey().trim();
            Object value = entry.getValue();
            String newKey = StringUtils.isEmpty(key) ? previousKey : previousKey + "." + key;

            boolean assignableFrom = ClassUtils.isAssignable(value.getClass(), Map.class);
            if (assignableFrom) {
                prop = JacksonUtil.from(JacksonUtil.to(value), new TypeReference<HashMap<String, Object>>() {});
                result = recursion(newKey, result, prop);
            } else {
                result.put(newKey, value);
            }
        }
        return result;
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
