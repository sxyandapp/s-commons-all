import java.util.HashMap;
import java.util.Map;

import com.simon.commonsall.utils.MapUtils;

/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */

/** 
 * <pre>
 * MapUtilsTest 
 * </pre>
 * @author  ShiXiaoyong 
 * @date    2017年5月17日
 * @version 1.0 
 */
public class MapUtilsTest {
	
	public static void main(String[] args) {
		Map<String, Object> m1 = new HashMap<>();
		Map<String, Object> m2= new HashMap<>();
		m1.put("a", "a");
		
		MapUtils.copy(m1, m2);
		System.out.println(m1);
		System.out.println(m2);
		
	}
	
}