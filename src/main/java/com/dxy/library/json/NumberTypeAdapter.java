package com.dxy.library.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.lang.math.NumberUtils;

import java.io.IOException;

/**
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
    public Number read(JsonReader jsonReader) throws IOException {
        if (c == Short.class) {
            return NumberUtils.toShort(jsonReader.nextString());
        } else if (c == Integer.class) {
            return NumberUtils.toInt(jsonReader.nextString());
        } else if (c == Long.class) {
            return NumberUtils.toLong(jsonReader.nextString());
        } else if (c == Float.class) {
            return NumberUtils.toFloat(jsonReader.nextString());
        } else if (c == Double.class) {
            return NumberUtils.toDouble(jsonReader.nextString());
        } else {
            return NumberUtils.toInt(jsonReader.nextString());
        }
    }
}
