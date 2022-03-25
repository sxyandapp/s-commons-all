import com.simon.commonsall.utils.ObjectUtilsEx;
import com.simon.commonsall.utils.ObjectUtilsEx.QuartzScheduled;

/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */

/** 
 * ObjectUtilsTest 
 * @author  ShiXiaoyong 
 * @date    2019年5月28日
 */
public class ObjectUtilsTest {
    
    public static void main(String[] args) {
        Object invoke = ObjectUtilsEx.invoke(new ObjectUtilsTest(), "a", "");
        System.out.println();
        
    }
    
    @QuartzScheduled("a")
    public String a() {
        System.out.println();
        return "aaa";
    }
    
}
