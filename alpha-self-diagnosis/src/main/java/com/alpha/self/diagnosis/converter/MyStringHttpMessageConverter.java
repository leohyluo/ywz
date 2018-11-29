package com.alpha.self.diagnosis.converter;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xc.xiong on 2017/9/21.
 */
@Component
public class MyStringHttpMessageConverter extends MappingJackson2HttpMessageConverter {


    @Override
    public void setSupportedMediaTypes(List<MediaType> supportedMediaTypes) {
        supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        super.setSupportedMediaTypes(supportedMediaTypes);
    }
}
