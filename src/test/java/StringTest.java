import org.apache.commons.lang3.StringUtils;

/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */

/** 
 * <pre>
 * StringTest 
 * </pre>
 * @author  ShiXiaoyong 
 * @date    2017年6月5日
 * @version 1.0 
 */
public class StringTest {
	public static void main(String[] args) {
		String[] ss={"a","b"};
		System.out.println(StringUtils.startsWithAny("ccc", ss));
	}
}
