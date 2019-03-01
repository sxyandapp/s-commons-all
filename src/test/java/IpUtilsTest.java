import com.simon.commonsall.utils.IpUtils;

/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */

/** 
 * <pre>
 * IpUtilsTest 
 * </pre>
 * @author  ShiXiaoyong 
 * @date    2018年8月28日
 * @version 1.0 
 */
public class IpUtilsTest {
	
	public static void main(String[] args) {
		byte[] b = {1,2,-1,0,1,2,3,4,1,2,3,4,1,2,3,4};
		System.out.println(IpUtils.toIP6(b));
		b =new byte[] {-1,2,3,4};
		System.out.println(IpUtils.toIP4(b));
	}
}
