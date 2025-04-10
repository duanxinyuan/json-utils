package com.dxy.library.json.gson.adapter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Gson解析的Number类型的字段解析适配器
 * @author duanxinyuan
 * 2018/6/20 14:58
 */
public abstract class NumberTypeAdapter extends TypeAdapter<Number> {

    @Override
    public void write(JsonWriter jsonWriter, Number number) throws IOException {
        if (number != null) {
            jsonWriter.value(number);
        } else {
            jsonWriter.nullValue();
        }
    }

    @Override
    public final Number read(JsonReader jsonReader) {
        try {
            if (jsonReader.peek() == null) {
                return null;
            }
            String json = jsonReader.nextString();
            return doRead(json);
        } catch (Exception e) {
            return null;
        }
    }

    protected abstract Number doRead(String json);

    public static class ShortAdapter extends NumberTypeAdapter {

        @Override
        protected Number doRead(String json) {
            return NumberUtils.toShort(json);
        }
    }

    public static class BigShortAdapter extends NumberTypeAdapter {

        @Override
        protected Number doRead(String json) {
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            return Short.parseShort(json);
        }
    }

    public static class IntAdapter extends NumberTypeAdapter {

        @Override
        protected Number doRead(String json) {
            return NumberUtils.toInt(json);
        }
    }

    public static class BigIntAdapter extends NumberTypeAdapter {

        @Override
        protected Number doRead(String json) {
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            return Integer.parseInt(json);
        }
    }

    public static class LongAdapter extends NumberTypeAdapter {

        @Override
        protected Number doRead(String json) {
            return NumberUtils.toLong(json);
        }
    }

    public static class BigLongAdapter extends NumberTypeAdapter {

        @Override
        protected Number doRead(String json) {
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            return Long.parseLong(json);
        }
    }

    public static class FloatAdapter extends NumberTypeAdapter {

        @Override
        protected Number doRead(String json) {
            return NumberUtils.toFloat(json);
        }
    }

    public static class BigFloatAdapter extends NumberTypeAdapter {

        @Override
        protected Number doRead(String json) {
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            return Float.parseFloat(json);
        }
    }

    public static class DoubleAdapter extends NumberTypeAdapter {

        @Override
        protected Number doRead(String json) {
            return NumberUtils.toDouble(json);
        }
    }

    public static class BigDoubleAdapter extends NumberTypeAdapter {

        @Override
        protected Number doRead(String json) {
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            return Double.parseDouble(json);
        }
    }

    public static class BigDecimalAdapter extends NumberTypeAdapter {

        @Override
        protected Number doRead(String json) {
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            return new BigDecimal(json);
        }
    }

    public static class AtomicIntegerAdapter extends NumberTypeAdapter {

        @Override
        protected Number doRead(String json) {
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            return new AtomicInteger(NumberUtils.toInt(json));
        }
    }

    public static class AtomicLongAdapter extends NumberTypeAdapter {

        @Override
        protected Number doRead(String json) {
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            return new AtomicLong(NumberUtils.toLong(json));
        }
    }

}
