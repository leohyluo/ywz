package com.alpha.commons.core.util;

import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by HP on 2018/9/30.
 */
public class MapToJsonUtil {
    public static String map2Json(Map<Object,Object> map){
        return new Gson().toJson(map);
    }
}
