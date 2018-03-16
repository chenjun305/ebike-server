package net.zriot.ebike.common.util;

import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.zriot.ebike.common.log.LogHelp;
import net.zriot.ebike.common.exception.GException;

/**
 * Created by ChenJun on 2018/3/10.
 */
public final class JacksonUtil {

    public static ObjectMapper objectMapper;

    /**
     * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
     * (1)转换为普通JavaBean：readValue(json,Student.class)
     * (2)转换为List,如List<Student>,将第二个参数传递为Student
     * [].class.然后使用Arrays.asList();方法把得到的数组转换为特定类型的List
     *
     * @param jsonStr
     * @param valueType
     * @return
     * @throws GException
     */
    public static <T> T readValue(String jsonStr, Class<T> valueType) throws GException {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.readValue(jsonStr, valueType);
        } catch (Exception e) {
            throw new GException(e);
        }
    }

    /**
     * json数组转List
     *
     * @param jsonStr
     * @param valueTypeRef
     * @return
     * @throws GException
     */
    public static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef) throws GException {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
            throw new GException(e);
        }
    }

    /**
     * 把JavaBean转换为json字符串
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            LogHelp.doLogException(e);
        }

        return null;
    }


    /**
     *
     * 获取jsonnode
     *
     * @param obj
     * @return
     */
    public static JsonNode readTree(Object obj) {
        String json = null;
        if (!(obj instanceof String)) {
            json = toJson(obj);
        }else {
            json = (String) obj;
        }
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.readTree(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取指定key的value
     *
     *
     * @param obj
     * @param key
     * @param delQuotes 是否需要删除首尾引号
     * @return
     */
    public static String get(Object obj, String key, boolean delQuotes) {
        JsonNode node = null;
        if(!(obj instanceof JsonNode)) {
            node = readTree(obj);
        }else {
            node = (JsonNode) obj;
        }

        JsonNode child = node;
        String[] keys = key.split("[.]");
        for (String k : keys) {
            child = child.get(k);
            if (child == null) {
                return null;
            }
        }

        String value = child.toString();
        if(delQuotes) {
            if(value.startsWith("\"")) {
                value = value.substring(1);
            }

            if (value.endsWith("\"")) {
                value = value.substring(0, value.length() - 1);
            }
        }

        return value;
    }

    public static String addAttributes(String key, Object value, Object d) throws GException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(key, value);
        return addAttributes(map, d);
    }

    @SuppressWarnings("unchecked")
    public static String addAttributes(HashMap<String, Object> ext, Object d) throws GException {
        String data = "";
        if (!(d instanceof String)) {
            data = toJson(d);
        }else {
            data = (String) d;
        }
        HashMap<String, Object> map = readValue(data, HashMap.class);
        map.putAll(ext);
        return toJson(map);
    }


    /**
     * 给json中数值value 添加双引号，仅仅适用于接口
     * @param data
     * @return
     */
    public static String addQuotesToData(String data) {
        int mark1 = 0;
        int mark2 = 0;
        String newData = "";
        for(int i =0;i<data.length();i++){
            char cx = data.charAt(i);
            if(mark1 >0 && mark2 == 0){
                if(cx != '"'){
                    newData +='\"';
                    mark2=1;
                }else{
                    mark2=2;
                }
            }
            if(mark1 >0){
                //System.out.print(cx);
            }
            if(cx==':'){
                mark1 ++;
            }
            if(cx==',' || cx == '}'){
                if(mark2==1){
                    newData+='\"';
                }
                mark1 =0;mark2=0;
            }
            newData += cx;
        }

        return newData;
    }

    /**
     * 修改json中指定key的值， 如果key不存在直接添加
     *
     * @param key
     * @param value
     * @param d
     * @return
     * @throws GException
     */
    public static String changeParams(String key, Object value, Object d) throws GException {
        String data = "";
        if (!(d instanceof String)) {
            data = toJson(d);
        }else {
            data = (String) d;
        }
        @SuppressWarnings("unchecked")
        HashMap<String, Object> map = readValue(data, HashMap.class);
        map.put(key, value);
        return toJson(map);
    }

    /**
     * 删除json中指定key的值
     *
     * @param key
     * @param value
     * @param d
     * @return
     * @throws GException
     */
    public static String removeParams(String key, Object value, Object d) throws GException {
        String data = "";
        if (!(d instanceof String)) {
            data = toJson(d);
        }else {
            data = (String) d;
        }
        @SuppressWarnings("unchecked")
        HashMap<String, Object> map = readValue(data, HashMap.class);
        map.remove(key);
        if (map.size() == 0) {
            return null;
        }
        return toJson(map);
    }
}

