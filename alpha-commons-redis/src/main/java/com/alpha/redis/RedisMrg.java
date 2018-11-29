package com.alpha.redis;

import com.alpha.commons.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * REDIS处理类
 *
 * @author cyfu
 */

public class RedisMrg {

    private static final Logger logger = LoggerFactory.getLogger(RedisMrg.class);


    private static JedisPool jedisPool;

//    private static String redisIp = ConfReaderImpl.getValueFromMe("redis_ip", "192.168.29.191");
//    private static String redisPwd = ConfReaderImpl.getValueFromMe("redis_pass", "redis_ywz");
//    private static Integer redisPort = Integer.parseInt(ConfReaderImpl.getValueFromMe("redis_port", "6379"));

    /**
     * //0:密码字段必要  1:密码字段非必要
     */
    private static Integer requirePass = Integer.parseInt(ConfReaderImpl.getValueFromMe("require_pass", "0"));

    /**
     * 数据库1
     */
    public static Integer DB1 = 1;

    /**
     * 数据库2
     */
    public static Integer DB2 = 2;

    /**
     * 数据库3
     */
    public static Integer DB3 = 3;

    /**
     * 数据库4
     */
    public static Integer DB4 = 4;

    /**
     * 数据库5
     */
    public static final int DB5 = 5;

    /**
     * 数据库6
     */
    public static final int DB6 = 6;

    private static RedisMrg redisMrg = null;

    public static void main(String[] args) {
        //RedisMrg.getInstance("192.168.29.191", "6380", "redis_ywz").setKey("FF", "1", RedisMrg.DB2);
        RedisMrg.getInstance("192.168.29.191", "6380", "redis_ywz").delKey("FF", RedisMrg.DB2);

    }

    private RedisMrg() {

    }

    private RedisMrg(String redisIp, String redisPort, String redisPwd) {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(500);
            config.setMaxIdle(100);
            config.setMaxWaitMillis(1000l);

            if (0 == requirePass) {
                jedisPool = new JedisPool(config, redisIp, Integer.parseInt(redisPort), 100000, redisPwd);
                logger.info("init jedis success...have password");
            } else if (1 == requirePass) {
                jedisPool = new JedisPool(config, redisIp, Integer.parseInt(redisPort), 100000);
                logger.info("init jedis success...have not password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("init jedis error..." + e.getMessage());
        }
    }

    public static RedisMrg getInstance(String redisIp, String redisPort, String redisPwd) {
        if (null != redisMrg) {
            return redisMrg;
        } else {
            redisMrg = new RedisMrg(redisIp, redisPort, redisPwd);
        }
        return redisMrg;
    }

    public synchronized static void setKey(String key, String value, int db) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(db);
            if (jedis.exists(key))
                jedis.del(key);
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            shutDestroy(jedis);
        }
    }

    public void setKey(String key, Integer value, int dbNum) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(dbNum);
            jedis.set(key, value.toString());
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            shutDestroy(jedis);
        }
    }

    /**
     * 更新KEY中的内容
     * @param tempKey
     * @param obj
     * @param dbNum
     */
    public void updateKey(String tempKey, Integer obj, int dbNum) {
        this.setKey(tempKey, obj, dbNum);
    }

    /**
     * 获取指定匹配后的KEYS
     * @param db
     * @param pattern
     * @return
     */
    public synchronized Set<String> getKeys(int db, String pattern){
        Set<String> keys = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(db);
            keys = jedis.keys(pattern);
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            shutDestroy(jedis);
        }
        return keys;
    }

    public synchronized void setKey(byte[] key, byte[] value, int db) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(db);
            if (jedis.exists(key))
                jedis.del(key);
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            shutDestroy(jedis);
        }
    }

    private static void setKey(byte[] key, byte[] value, int seconds, int db) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(db);
            jedis.setex(key, seconds, value);
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            shutDestroy(jedis);
        }
    }

    public synchronized void clearList(byte[] key, int db) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(db);
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            shutDestroy(jedis);
        }
    }

    public synchronized void clearList(String key, int db) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(db);
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            shutDestroy(jedis);
        }
    }

    public synchronized void lpush(byte[] key, byte[] value, int db) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(db);
            jedis.lpush(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            shutDestroy(jedis);
        }
    }

    public synchronized <T> void lpush(String key, T t, int db) {
        Jedis jedis = null;
        try {
            String jsonStr = JsonUtils.serrializeObject(t);
            jedis = jedisPool.getResource();
            jedis.select(db);
            jedis.lpush(key, jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            shutDestroy(jedis);
        }
    }

    public synchronized void delKey(byte[] key, int db) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(db);
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            shutDestroy(jedis);
        }
    }

    public synchronized void delKey(String key, int db) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(db);
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            shutDestroy(jedis);
        }
    }

    public synchronized Object getKey(String key, int db) {
        Object obj = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(db);
            byte[] in = jedis.get(key.getBytes());
            if (in != null) {
                obj = ObjectsTranscoder.unserialize(in);
            }
            if (null == obj) {
                String content = jedis.get(key);
                return content;
            }
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
            return obj;
        } finally {
            shutDestroy(jedis);
        }

        return obj;
    }

    public List<Object> lrange(String key, int db) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(db);
            List<byte[]> list = jedis.lrange(key.getBytes(), 0, -1);
            List<Object> objectList = list.stream().map(e -> ObjectsTranscoder.unserialize(e)).collect(Collectors.toList());
            return objectList;
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
            return null;
        } finally {
            shutDestroy(jedis);
        }
    }

    public Object getKeyEm(String key, int db) {
        Object obj = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(db);
            byte[] in = jedis.get(key.getBytes());
            String content = jedis.get(key);
            obj = ObjectsTranscoder.unserialize(in);
            if (null == obj)
                return content;
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
            return obj;
        } finally {
            shutDestroy(jedis);
        }
//		Object obj = RedisClusterMrg.getKey(key.getBytes());
        return obj;
    }

    private synchronized static Object getEumKey(byte[] key, int db) {
        Object obj = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(db);
            obj = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
            return obj;
        } finally {
            shutDestroy(jedis);
        }

        return obj;
    }

    public void incr(String key, int db) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(db);
            if (jedis.exists(key)) {
                jedis.incr(key);
            } else {
                jedis.set(key, "1");
            }

        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            shutDestroy(jedis);
        }
    }

    private static void cleanAll() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.flushAll();
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            shutDestroy(jedis);
        }
    }

    /**
     * 刷新生存时间
     *
     * @param key
     * @param seconds
     * @param db
     */
    private static void expire(String key, int seconds, int db) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(db);
            jedis.expire(key, seconds);
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            shutDestroy(jedis);
        }

    }

    private static void shutDestroy(Jedis... jedis) {
        if (null != jedis)
            jedisPool.returnResource(jedis[0]);
    }

    public synchronized void setKeyAndExpire(String key, String value, int seconds, int db) {
        try {
            Jedis jedis = jedisPool.getResource();
            jedis.select(db);
            jedis.set(key, value);
            jedis.expire(key, seconds);
            jedis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized String getKey2(String key, int DB1) {
        String str = null;
        try {
            Jedis jedis = jedisPool.getResource();
            jedis.select(DB1);
            str = jedis.get(key);
            jedis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    public synchronized void setSetString(List<String> list, String key, int DB1) {
        try {
            Jedis jedis = jedisPool.getResource();
            jedis.select(DB1);
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    jedis.sadd(key, list.get(i));
                }
            }
            jedis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public synchronized static List<String> getSetString(String key, int DB1) {
        List<String> list = null;
        try {
            Jedis jedis = jedisPool.getResource();
            jedis.select(DB1);
            Set<String> strings = jedis.smembers(key);
            list = new ArrayList<>(strings);
            jedis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public synchronized  static void delSetValue(String key,String value,int db13){
        try {
            Jedis jedis = jedisPool.getResource();
            jedis.select(db13);
            jedis.srem(key,value);
            jedis.expire(key, 30 * 60);
            jedis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
