import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.simon.commonsall.utils.MapUtilsEx;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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
@Slf4j
public class MapUtilsTest {
	
	public static void main(String[] args) {
		Map<String, Object> m1 = new HashMap<>();
//		Map<String, Object> m2= new HashMap<>();
		Object o = new  Object();
		m1.put("a", o);
		m1.put("b", o);
		
//		MapUtils.copy(m1, m2);
//		System.out.println(m1);
//		System.out.println(m2);
		
		MapUtilsEx.removeByValue(m1, o);
		System.out.println();
		
	}
}
