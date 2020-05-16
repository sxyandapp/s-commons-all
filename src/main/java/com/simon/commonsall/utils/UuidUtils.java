package com.simon.commonsall.utils;

import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 描述：UuidUtils
 * 
 * @author Shixy
 * @date 2015年6月29日
 * @since 1.0
 */
public class UuidUtils {

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String guid() {
        return uuid().replace("-", "");
    }
    
    /**
     * @author ShiXiaoyong
     * @date   2019年12月4日
     * @param length
     * @return
     * @deprecated use {@link RandomStringUtils}
     */
    @Deprecated
    public static String guidDigital(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

}
