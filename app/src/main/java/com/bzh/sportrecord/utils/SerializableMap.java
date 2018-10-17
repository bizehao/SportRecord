package com.bzh.sportrecord.utils;

import java.io.Serializable;
import java.util.Map;

/**
 * @author 毕泽浩
 * @Description: 序列化的map
 * @time 2018/10/16 22:28
 */
public class SerializableMap<T> implements Serializable {

    private Map<String, T> map;

    public Map<String, T> getMap() {
        return map;
    }

    public void setMap(Map<String, T> map) {
        this.map = map;
    }
}
