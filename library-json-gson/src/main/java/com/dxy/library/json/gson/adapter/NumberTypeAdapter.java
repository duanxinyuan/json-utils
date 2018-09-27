package com.dxy.library.json.gson.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Gson反序列化的Number类型的字段反序列化适配器
 * @author duanxinyuan
 * 2018/6/20 14:58
 */
public class NumberTypeAdapter extends TypeAdapter<Number> {
    private Class c;

    public NumberTypeAdapter(Class c) {
        this.c = c;
    }

    @Override
    public void write(JsonWriter jsonWriter, Number number) throws IOException {
        jsonWriter.value(number);
    }

    @Override
    public Number read(JsonReader jsonReader) {
        try {
            String json = jsonReader.nextString();
            if (c == short.class) {
                return NumberUtils.toShort(json);
            } else if (c == Short.class) {
                return Short.parseShort(json);
            } else if (c == int.class) {
                return NumberUtils.toInt(json);
            } else if (c == Integer.class) {
                return Integer.parseInt(json);
            } else if (c == long.class) {
                return NumberUtils.toLong(json);
            } else if (c == Long.class) {
                return Long.parseLong(json);
            } else if (c == float.class) {
                return NumberUtils.toFloat(json);
            } else if (c == Float.class) {
                return Float.parseFloat(json);
            } else if (c == double.class) {
                return NumberUtils.toDouble(json);
            } else if (c == Double.class) {
                return Double.parseDouble(json);
            } else if (c == BigDecimal.class) {
                return new BigDecimal(json);
            } else {
                return Integer.parseInt(json);
            }
        } catch (Exception e) {
            return null;
        }
    }
}
