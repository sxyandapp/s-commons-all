import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */

/** 
 * Base64Test 
 * @author  ShiXiaoyong 
 * @date    2019年12月16日
 */
public class Base64Test {
    
    public static void main(String[] args) {
        String original="";
        byte[] decodeBase64 = Base64.decodeBase64(original);
        String encodeBase64String = Base64.encodeBase64String(decodeBase64);
        System.out.println(encodeBase64String);
        System.out.println(StringUtils.equals(encodeBase64String, original));
    }
}
