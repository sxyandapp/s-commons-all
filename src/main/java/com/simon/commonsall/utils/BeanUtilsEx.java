/*
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved.
 */
package com.simon.commonsall.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 * BeanUtils 
 * </pre>
 * @author ShiXiaoyong
 * @date 2017年8月23日
 * @version 1.0
 */
public class BeanUtilsEx {

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();

        simpleModule.setDeserializerModifier(new BeanDeserializerModifier() {
            @Override
            public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
                Class<?> beanClass = beanDesc.getBeanClass();
                if (beanClass.equals(Object.class)) {
                    return new JsonDeserializer<Object>() {
                        @Override
                        public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                            if (p.getCurrentToken() == JsonToken.VALUE_STRING) {
                                String valueAsString = p.getValueAsString();
                                String ss = StringUtils.substringBetween(valueAsString, "`~`", "`~`");
                                if (StringUtils.isNotEmpty(ss)){
                                    return new Date(Long.parseLong(ss));
                                }
                            }
                            return deserializer.deserialize(p,ctxt);
                        }
                    };
                }
                return super.modifyDeserializer(config, beanDesc, deserializer);
            }
        });

        simpleModule.addSerializer(Date.class, new JsonSerializer<Date>() {
            @Override
            public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                gen.writeObject(StringUtils.wrap(StringUtilsEx.valueOf(value.getTime()),"`~`"));
            }
        });
        mapper.registerModule(simpleModule);
    }

    public static Map<?, ?> describe(final Object bean) {
        return mapper.convertValue(bean, Map.class);
    }

    // public static Map<?, ?> describe(final Object bean) {
    //     MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    //     return mapperFactory.getMapperFacade(Object.class, Map.class).map(bean);
    // }
}
