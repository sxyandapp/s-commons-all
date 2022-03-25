/*
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved.
 */
package com.simon.commonsall.utils;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * NumberUtils
 * 
 * @author ShiXiaoyong
 * @date 2019年12月21日
 */
public class NumberUtilsEx {

    public static Long toLong(final Object answer) {
        Number number = null;
        if (answer != null) {
            if (answer instanceof Number) {
                number = (Number) answer;
            } else if (answer instanceof String) {
                try {
                    String text = (String) answer;
                    number = NumberFormat.getInstance().parse(text);
                } catch (ParseException e) {
                    // failure means null is returned
                }
            }
        }

        if (number == null) {
            return null;
        } else if (number instanceof Long) {
            return (Long) number;
        }
        return new Long(number.longValue());
    }
    
    public static Integer toInteger(final Object answer) {
        Number number = null;
        if (answer != null) {
            if (answer instanceof Number) {
                number = (Number) answer;
            } else if (answer instanceof String) {
                try {
                    String text = (String) answer;
                    number = NumberFormat.getInstance().parse(text);
                } catch (ParseException e) {
                    // failure means null is returned
                }
            }
        }
        
        if (number == null) {
            return null;
        } else if (number instanceof Integer) {
            return (Integer) number;
        }
        return new Integer(number.intValue());
    }

}
