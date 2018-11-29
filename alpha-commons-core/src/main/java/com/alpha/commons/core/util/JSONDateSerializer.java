package com.alpha.commons.core.util;

import com.alpha.commons.util.DateUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

/**
 * JSON 日期序列化
 */
public class JSONDateSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if(date != null) {
            jsonGenerator.writeString(DateUtils.date2String(date, DateUtils.DATE_TIME_FORMAT));
        }
    }
}
