import com.simon.commonsall.utils.ThreadUtils;

/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */

/** 
 * <pre>
 * ThreadTest 
 * </pre>
 * @author  ShiXiaoyong 
 * @date    2019年3月21日
 * @version 1.0 
 */
public class ThreadTest {
    
    private static boolean a = false;
    private static volatile int ii = 0;
	
	public static void main(String[] args) throws Exception {
	    test3();
	}
	
	static void test1() throws Exception{
//	     Object.class.wait();
//      Thread thread = new Thread() {
//          @Override
//          public void run() {
//              System.out.println(Thread.currentThread().getName());
//          }
//      };
//      thread.start();
//      ThreadUtils.sleepSeconds(2);
//      thread.start();
	}
	
    static void test2() throws Exception {
        ThreadUtils.run(() -> {
            System.out.println("start");
            while (!a) {
//                ThreadUtils.sleep(5);
            }
            System.out.println("end");
        });
        ThreadUtils.run(() -> {
            ThreadUtils.sleep(1000);
            a = true;
        });
    }
    
    static void test3() throws Exception {
        for (int i = 0; i < 100000; i++) {
            ThreadUtils.run(() -> {
                ii++;
            });
        }
        ThreadUtils.sleepSeconds(10);
        System.out.println(ii);
    }
	
}
