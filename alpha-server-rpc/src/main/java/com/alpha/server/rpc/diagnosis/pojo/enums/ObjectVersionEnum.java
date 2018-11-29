package com.alpha.server.rpc.diagnosis.pojo.enums;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xc.xiong on 2017/10/18.
 * 对象版本
 */
public enum ObjectVersionEnum {

    儿童版(1),
    妇女版(2),
    老人版(4);


    private int ord;
    private int value;

    ObjectVersionEnum(int value) {
        this.value = value;
        ord = 1 << ordinal();
    }


    public static List<ObjectVersionEnum> exclude(ObjectVersionEnum... enums) {
        int toSub = 0;
        for (ObjectVersionEnum f : enums) {
            toSub += f.getOrd();

        }
        return getSupportTypes(getSum() - toSub);
    }

    public static List<ObjectVersionEnum> getSupportTypes(int ord) {
        if(ord==0)
            return getSumObjectVersion();
        List<ObjectVersionEnum> result = new ArrayList<>();
        for (ObjectVersionEnum f : values()) {
            if ((ord & f.getOrd()) > 0) {
                result.add(f);
            }
        }
        return result;
    }


    public static List<ObjectVersionEnum> getSumObjectVersion() {
        List<ObjectVersionEnum> result = Arrays.asList(ObjectVersionEnum.values());
        return result;
    }

    public static int getSum() {
        int result = 0;
        for (ObjectVersionEnum f : values()) {
            result += f.getOrd();
        }
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getOrd() {
        return ord;
    }

    public void setOrd(int ord) {
        this.ord = ord;
    }

}
