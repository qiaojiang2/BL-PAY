package cn.jishuyuan.pay.base.common.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * @author qiao <jiangqiao, 1468325120@qq.com>
 * @version v1.0
 * @project bl-pay
 * @Description JSON工具类
 * @encoding UTF-8
 * @date 2017/6/28
 * @time 12:55
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class JsonUtil {
    /**
     * 将JSON字符串转换为泛型对象
     *
     * @param jsonString
     * @param c
     * @param <T>
     * @return
     */
    public static <T> T jsonStringToObject(String jsonString, Class<T> c) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return (T) mapper.readValue(jsonString, c);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将对象转换成JSON字符串
     *
     * @param obj
     * @return
     */
    public static String objectToJsonString(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Map对象转换为JSON字符串
     *
     * @param map
     * @return
     */
    public static String mapToJson(Map<String, ?> map) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Object对象转换为Map对象
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> objectToMap(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(SerializationFeature.INDENT_OUTPUT);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.convertValue(obj, Map.class);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Json字符串直接转换为Map对象方法
     *
     * @param json
     * @return
     */
    public static Map<String, Object> jsonToMap(String json) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = null;
        try {
            map = mapper.readValue(json, Map.class);
            System.out.println(map.size());
            Iterator<?> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                Object key = iterator.next();
                System.out.print(key + ":");
                System.out.println(map.get(key).toString());
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

}
