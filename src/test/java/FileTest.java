import java.io.File;

/*  
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved. 
 */

/** 
 * <pre>
 * FileTest 
 * </pre>
 * @author  ShiXiaoyong 
 * @date    2018年5月10日
 * @version 1.0 
 */
public class FileTest {
	
	public static void main(String[] args) {
		File f = new File("d:/1/");
		System.out.println(f.exists());
		File ff = new File(f, "2.log");
		System.out.println(ff.exists());
	}
	
}
