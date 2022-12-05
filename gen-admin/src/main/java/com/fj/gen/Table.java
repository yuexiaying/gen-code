package com.fj.gen;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.TableMap;
import cn.hutool.core.util.ObjectUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * tableName fileName
 * @author fjding
 * @date 2022/12/4
 */
public class Table <S,T>{

    private Map<String,Map<S,T>> map = new HashMap<>();

    private TableMap<S,T> valueTableMap = new TableMap<>();

    private TableMap<S,String> keyTableMap = new TableMap<>();

    public static <S,T> Table<S,T> getInstance(){
        return new Table<>();
    }

    public T get(String key,S subKey){
        return map.get(key).get(subKey);
    }

    public List<String> getKeys(){
        return ListUtil.toList(map.keySet());
    }

    public List<T> getValuesBySubKey(S subKey){
        return valueTableMap.getValues(subKey);
    }

    public T get(S subKey){
        return valueTableMap.get(subKey);
    }

    public String getKey(S subKey){
        return keyTableMap.get(subKey);
    }

    public List<T> getValues(String key){
        return ListUtil.toList(map.get(key).values());
    }

    public void add(String key,S subKey,T value){
        Map<S, T> stMap = map.get(key);
        if (ObjectUtil.isNull(stMap)){
            stMap = new HashMap<>();
            map.put(key,stMap);
        }
        stMap.put(subKey,value);
        valueTableMap.put(subKey,value);
        keyTableMap.put(subKey,key);
    }

}
