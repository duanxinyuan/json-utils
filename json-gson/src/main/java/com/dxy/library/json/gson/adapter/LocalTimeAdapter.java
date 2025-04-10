/*
 * Alipay.com Inc.
 * Copyright (c) 2004-2025 All Rights Reserved.
 */
package com.dxy.library.json.gson.adapter;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 *
 * @author qijian
 * @version LocalDateTimeAdapter.java, v 0.1 2025年04月10日 18:10 qijian
 */
public class LocalTimeAdapter extends TypeAdapter<LocalTime> {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public void write(JsonWriter out, LocalTime value) throws IOException {
        if (value != null) {
            out.value(dateTimeFormatter.format(value));
        } else {
            out.nullValue();
        }
    }

    @Override
    public LocalTime read(JsonReader in) throws IOException {
        return LocalTime.parse(in.nextString(), dateTimeFormatter);
    }

}