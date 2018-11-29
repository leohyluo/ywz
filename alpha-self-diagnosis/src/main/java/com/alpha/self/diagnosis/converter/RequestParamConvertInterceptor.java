package com.alpha.self.diagnosis.converter;

import com.alibaba.fastjson.JSON;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.RequestFacade;
import org.apache.catalina.util.ParameterMap;
import org.apache.tomcat.util.buf.MessageBytes;
import org.apache.tomcat.util.http.Parameters;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by xc.xiong on 2017/10/16.
 */
@Controller
public class RequestParamConvertInterceptor implements HandlerInterceptor {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private Map<String, Object> parameters;
    private Map<String, String> baseReqKeyMap = new HashMap<>();

    static {
        objectMapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        objectMapper.setSerializationConfig(objectMapper.getSerializationConfig());
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if (request.getContentType() == null) {
            System.out.println("request.getContentType()  is null !!");
            return true;
        }
        if (request.getContentType().contains("application/json") && request instanceof RequestFacade) {
            parameters = new TreeMap<>();
            Class clazz = request.getClass();
            Field field = clazz.getDeclaredField("request");
            field.setAccessible(true);
            Request baseRequest = (Request) field.get(request);

            Class coyoteClazz = baseRequest.getClass();
            Field coyoteField = coyoteClazz.getDeclaredField("coyoteRequest");
            coyoteField.setAccessible(true);
            org.apache.coyote.Request coyoteRequest = (org.apache.coyote.Request) coyoteField.get(baseRequest);

            MessageBytes messageBytes = MessageBytes.newInstance();
            messageBytes.setString("application/x-www-form-urlencoded");
            coyoteRequest.setContentType(messageBytes);

            Field parameterMapField = coyoteClazz.getDeclaredField("parameterMap");
            parameterMapField.setAccessible(true);
            ParameterMap<String, String[]> parameterMap = (ParameterMap<String, String[]>) parameterMapField.get(baseRequest);

            ServletInputStream inputStream = request.getInputStream();
            String result = readInputStream(inputStream);
            readParams(result);

            baseRequest.getParameterMap();
            setAttributes(request);

            parameterMap.setLocked(false);
            parameterMap.putAll(parameterMap);
            parameterMap.setLocked(true);

            Parameters params = coyoteRequest.getParameters();
//            baseRequest.getParameter("answers");
//            params.addParameter();
            addParameters(params);


        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public void addParameters(Parameters param) {
        try {
            Class coyoteClazz = param.getClass();
            Field coyoteField = coyoteClazz.getDeclaredField("paramHashValues");
            coyoteField.setAccessible(true);
            Map<String, ArrayList<String>> paramHashValues = (Map<String, ArrayList<String>>) coyoteField.get(param);
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                Object value = entry.getValue();
                if (value != null) {
                    if (value instanceof Collection<?>) {
                        Collection<?> a = (Collection<?>) value;
                        ArrayList<String> b = (ArrayList<String>) value;
                        ArrayList<String> v = new ArrayList<String>();
                        for (Object e : a) {
                            v.add(e.toString());
                        }
                        paramHashValues.put(entry.getKey(), b);
                    } else {
                        ArrayList<String> v = new ArrayList<>();
                        v.add(JSON.toJSONString(value));
                        paramHashValues.put(entry.getKey(), v);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAttributes(HttpServletRequest request) {
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
    }

    public String readInputStream(ServletInputStream in) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int length = 0;
            while ((length = in.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
            }
            String result = new String(bos.toByteArray(), "UTF-8");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
            }
        }
        return null;
    }

    public void readParams(String result) {
        Map<String, Object> req = null;
        try {
            req = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {
            });
            if (req != null) {
                addMapEntries(req);
            }
        } catch (Exception e) {
        }
    }

    private void addMapEntries(Map<String, Object> req) {
        if (req != null && !req.isEmpty()) {
            for (Map.Entry<String, Object> prop : req.entrySet()) {
                Object value = prop.getValue();
                if (value != null) {
//                    if (value instanceof Collection<?>) {
//                        Collection<?> a = (Collection<?>) value;
//                        List<String> v = new ArrayList<String>();
//                        for (Object e : a) {
//                            v.add(e.toString());
//                        }
//                        parameters.put(getKey(prop.getKey()), v.toArray(new String[0]));
////                        parameters.put(getKey(prop.getKey()), new String[]{JSON.toJSONString(v)});
//                    } else {
//                        String svalue = value.toString();
//                        parameters.put(getKey(prop.getKey()), new String[]{svalue});
//                    }
                    parameters.put(getKey(prop.getKey()), value);
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

}
