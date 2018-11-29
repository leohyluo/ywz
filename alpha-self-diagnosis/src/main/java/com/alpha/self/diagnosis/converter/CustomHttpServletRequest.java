/**
 * Project Name: 智慧药师大众版
 * File Name: CustomHttpServletRequest.java
 * Package Name: com.zhys.server.servlet
 * Date: 2016年4月7日上午11:07:40
 * Copyright (c) 2016, 深圳智慧药师股份有限公司  All Rights Reserved.
 */
package com.alpha.self.diagnosis.converter;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.*;

/**
 * 自定义的HttpServletRequest封装类，对HttpServletRequest进行封装，以便对参数中存在的加密字段进行解密并置换
 *
 * @author jianghu
 * @date 2016年4月7日 上午11:07:40 <br/>
 */
public class CustomHttpServletRequest extends HttpServletRequestWrapper {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static Map<String, String> baseReqKeyMap = new HashMap<String, String>();
    // modified parameters map
    private final Map<String, String[]> parameters = new TreeMap<String, String[]>();
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomHttpServletRequest.class);

    static {
        objectMapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
        objectMapper.setSerializationConfig(objectMapper.getSerializationConfig());
    }


    public CustomHttpServletRequest(HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
        super(request);
        String params = request.getParameter("allParam");
        parameters.putAll(super.getParameterMap());
        if (StringUtils.isBlank(params)) {
            return;
        }
        Map<String, Object> req = null;
        try {
            req = objectMapper.readValue(params, new TypeReference<Map<String, Object>>() {
            });
            if (req != null) {
                addMapEntries(req);
            }
        } catch (Exception e) {
            try {
                req = StringToMap(params.substring(1, params.length() - 1));
                if (req != null) {
                    addMapEntries(req);
                }
            } catch (Exception e1) {
                e.printStackTrace();
                e1.printStackTrace();
            }
        }
        parameters.put("allParam", new String[]{params});
    }

    private void addMapEntries(Map<String, Object> req) {
        if (req != null && !req.isEmpty()) {
            for (Map.Entry<String, Object> prop : req.entrySet()) {
                Object value = prop.getValue();
                if (value != null) {
                    if (value instanceof Collection<?>) {
                        Collection<?> a = (Collection<?>) value;
                        List<String> v = new ArrayList<String>();
                        for (Object e : a) {
                            v.add(e.toString());
                        }
                        parameters.put(getKey(prop.getKey()), v.toArray(new String[0]));
//                        parameters.put(getKey(prop.getKey()), new String[]{JSON.toJSONString(v)});
                    } else {
                        String svalue = value.toString();
                        parameters.put(getKey(prop.getKey()), new String[]{svalue});
                    }
                }
            }
        }
    }

    private String getKey(String key) {
        String v = baseReqKeyMap.get(key);
        if (v == null) {
            return key;
        }
        return v;
    }

    @Override
    public String getParameter(String name) {
        String[] strings = getParameterMap().get(name);
        if (strings != null) {
            return strings[0];
        }
        return super.getParameter(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        // Return an unmodifiable collection because we need to uphold the
        // interface contract.
        return Collections.unmodifiableMap(parameters);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(getParameterMap().keySet());
    }

    @Override
    public String[] getParameterValues(final String name) {
        return getParameterMap().get(name);
    }

    /**
     * 定义分割常量 （#在集合中的含义是每个元素的分割，|主要用于map类型的集合用于key与value中的分割）
     */
    private static final String SEP1 = "=";
    private static final String SEP2 = ",";

    public static Map<String, Object> StringToMap(String mapText) {

        if (mapText == null || mapText.equals("")) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        String[] text = mapText.split("\\" + SEP2); // 转换为数组
        for (String str : text) {
            String[] keyText = str.split(SEP1); // 转换key与value的数组
            if (keyText.length < 1) {
                continue;
            }
            String key = keyText[0].trim(); // key
            String value = keyText[1].trim(); // value
            if (value.charAt(0) == 'M') {
                Map<?, ?> map1 = StringToMap(value);
                map.put(key, map1);
            } else {
                map.put(key, value);
            }
        }
        return map;
    }
}
