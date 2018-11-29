package com.alpha.his.util;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.util.XStreamUtils;
import com.alpha.his.pojo.dto.ResultDTO;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class WSResponseUtils {

    public static String buildResponseMessage(ResultDTO resultDTO) {
        Map<String, Class> aliasMap = new HashMap<>();
        aliasMap.put("result", ResultDTO.class);
        String content = XStreamUtils.Object2XMLString(resultDTO, aliasMap);
        String result = GlobalConstants.XML_HEADER + content;
        return result;
    }

    public static String buildResponseMessage(Object obj) {
        Map<String, Class> aliasMap = new HashMap<>();
        aliasMap.put("root", obj.getClass());
        String content = XStreamUtils.Object2XMLString(obj, aliasMap);
        String result = GlobalConstants.XML_HEADER + content;
        return result;
    }
}
