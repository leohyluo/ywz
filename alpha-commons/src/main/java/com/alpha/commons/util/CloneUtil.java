package com.alpha.commons.util;

import org.springframework.context.annotation.Bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by xc.xiong on 2017/9/8.
 */
public class CloneUtil {

    /**
     * list 对象克隆
     *
     * @param list
     * @return
     */
    public static List cloneArray(List list) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bo);
            os.writeObject(list);
            ByteArrayInputStream bo1 = new ByteArrayInputStream(bo.toByteArray());
            ObjectInputStream os1 = new ObjectInputStream(bo1);
            return (List<Bean>) os1.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * list 对象克隆
     *
     * @param obj
     * @return
     */
    public static Object cloneObject(Object obj) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bo);
            os.writeObject(obj);
            ByteArrayInputStream bo1 = new ByteArrayInputStream(bo.toByteArray());
            ObjectInputStream os1 = new ObjectInputStream(bo1);
            return os1.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
