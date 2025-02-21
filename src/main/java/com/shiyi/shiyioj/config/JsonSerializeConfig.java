package com.shiyi.shiyioj.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonSerializeConfig {
    public static class CustomDateSerializer extends JsonSerializer<Date> {
        private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        @Override
        public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            String formattedDate = dateFormat.format(date);
            jsonGenerator.writeString(formattedDate);
        }
    }
}
