package com.simon.commonsall.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/** 
 * 描述：JsonUtils 
 * @author Shixy
 * @date   2015年8月9日
 * @since  1.0 
 */
@Slf4j
public class JsonUtils {

    private final static ObjectMapper mapper;
    private final static ObjectMapper mapper2;
    private final static int maxStringLength = 100;

    static private void generalConfig(ObjectMapper mapper) {
        mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
    }

    static {
        mapper = new ObjectMapper();
        generalConfig(mapper);
        mapper2 = new ObjectMapper();
        generalConfig(mapper2);
        // 额外配置mapper2
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(String.class, new JsonSerializer<String>() {
            @Override
            public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                gen.writeString(StringUtils.abbreviate(value, maxStringLength));
            }
        });
        simpleModule.addSerializer(String[].class, new JsonSerializer<String[]>() {
            @Override
            public void serialize(String[] value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                gen.writeStartArray(value.length);
                for (String s : value) {
                    gen.writeString(StringUtils.abbreviate(s, maxStringLength));
                }
                gen.writeEndArray();
            }
        });
        mapper2.registerModule(simpleModule);
    }

    /**
     * 根据Json值生成类实例.
     * 返回结果包含ArrayList和LinkedHashMap
     * Created by ShiXiaoyong on 2019/11/28 18:09.
     * @return 若生成失败返回Null
     */
    public static <T> T from(String jsonString) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return (T) mapper.readValue(jsonString, Object.class);
        } catch (Exception e) {
            log.warn("parse json string error", e);
        }
        return null;
    }

    /**
     * 根据Json值生成类实例
     * Created by ShiXiaoyong on 2019/7/30 20:45.
     * @return 若生成失败返回Null
     */
    public static <T> T from(String jsonString, Class<T> valueType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, valueType);
        } catch (Exception e) {
            log.warn("parse json string error", e);
        }
        return null;
    }

    /**
     * Created by ShiXiaoyong on 2019/7/30 20:45.
     */
    public static <T> T from(String jsonString, TypeReference<T> valueType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, valueType);
        } catch (Exception e) {
            log.warn("parse json string error", e);
        }
        return null;
    }

    /**
     * 将指定实例转为Json字符串
     * Created by ShiXiaoyong on 2019/7/30 20:45.
     * @return 若转换失败返回Null
     */
    public static String to(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            log.warn("parse to json string error", e);
        }
        return StringUtils.EMPTY;
    }

    /**
     * 测试json是否合法;如果入参为空，同样认为合法
     * Created by ShiXiaoyong on 2021/4/7 13:43.
     */
    public static boolean test(String jsonString) {
        if (StringUtils.isEmpty(jsonString)) {
            return true;
        }
        try {
            mapper.readValue(jsonString, Object.class);
        } catch (IOException ignore) {
            return false;
        }
        return true;
    }

    /**
     * 将指定实例转为Json字符串(若属性中含有超长字符串，其字符串会被截断显示)
     * Created by ShiXiaoyong on 2021/8/11 14:24.
     */
    public static String toEscape(Object object) {
        try {
            return mapper2.writeValueAsString(object);
        } catch (Exception e) {
            log.warn("[toEscape]parse to json string error:{}", e.getMessage());
        }
        return StringUtils.EMPTY;
    }
}
