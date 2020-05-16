import com.simon.commonsall.utils.HexUtils;
import java.io.File;
import java.util.Date;
import java.util.Map;

import com.simon.commonsall.utils.BeanUtilsEx;
import com.simon.commonsall.utils.JsonUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */

/** 
 * BeanUtilsExTest 
 * @author  ShiXiaoyong 
 * @date    2020年3月3日
 */
@Slf4j
public class BeanUtilsExTest {
    
    public static void main(String[] args) {
        A a=new A();
        a.i=1;
        a.s1="s1";
        a.cc=new File("d:/");
                
        B b = new B();
        b.b1="bbbbb";
        a.b=b;
        Map describe = BeanUtilsEx.describe(a);
        System.out.println(JsonUtils.to(describe));
        
        describe = BeanUtilsEx.describe(null);
        System.out.println(JsonUtils.to(describe));
    }

    @Test
    public void testDate(){
        Map<?, ?> describe = BeanUtilsEx.describe(new SubItem());
        log.debug("SubItem:{}",describe);
        describe = BeanUtilsEx.describe(new Item());
        log.debug("Item:{}",describe);
    }

    @Getter
    public static class Item{
        public Date d = new Date();
        public Date d2 = null;
        public int i=1;
        public String xxx="ff";
        SubItem s = new SubItem();
    }

    @Getter
    public static class SubItem{
        public Date d = new Date();
        public Date d2 = null;
        public int i=1;
        public String xxx="ff";
    }


    
    @Getter
    @Setter
    public static class A{
        String s1;
        int i;
        B b;
        File cc;
    }
    
    @Getter
    @Setter
    public static class B{
        String b1;
        A a;
    }
}
