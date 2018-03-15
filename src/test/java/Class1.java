import com.simon.commonsall.utils.ClassUtils;

/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */

/** 
 * <pre>
 * Class1 
 * </pre>
 * @author  ShiXiaoyong 
 * @date    2017年6月15日
 * @version 1.0 
 */
public class Class1<T> {

	/**
	 * Constructor
	 * @author ShiXiaoyong
	 * @date   2017年6月15日
	 */
	public Class1() {
		super();
		Class<? extends Class1> class1 = Class1.class;
		Class<T> persistentClass = (Class<T>) ClassUtils.getSuperClassGenricType(getClass(),class1, 0);
	}
	
}
