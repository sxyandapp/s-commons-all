import org.apache.commons.lang3.ArrayUtils;

import com.simon.commonsall.utils.ByteUtilsEx;
import com.simon.commonsall.utils.IpUtils;
import org.junit.Test;

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
		test2();
	}

	@Test
    public void test1() {
        byte[] b = {1,2,-1,0,1,2,3,4,1,2,3,4,1,2,3,4};
		System.out.println(IpUtils.toIP6(b));
		b =new byte[] {-1,2,3,4};
		System.out.println(IpUtils.toIP4(b));

        System.out.println(IpUtils.toIP6("2409:816c:2b6b:45b5::a3f2:dc64",false));
    }
    private static void test2() {
        String s = "129.129.129.255";
        byte[] fromIP4 = IpUtils.fromIP4(s);
        System.out.println((fromIP4[0]&0xff) +","+(fromIP4[1]&0xff) +","+(fromIP4[2]&0xff) +","+(fromIP4[3]&0xff));
    }
}
