package com.alpha.redis;


public class ObjectsTranscoder {
    public static byte[] serialize(Object object) {
        return SerializationUtil.serialize(object);
    }

    public static Object unserialize(byte[] bytes) {

        return SerializationUtil.deserialize(bytes);
    }
}
