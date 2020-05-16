import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.win32.W32APIOptions;

/*
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved.
 */

/**
 * <pre>
 * BeepUtilsTest
 * </pre>
 * 
 * @author ShiXiaoyong
 * @date 2019年8月27日
 * @version 1.0
 */
public class BeepUtilsTest {

	static Kernel32Ex kernel32;

	public static void main(String[] args) {
//		if (Platform.isWindows()) {
//			kernel32 = (Kernel32Ex) Native.loadLibrary("kernel32", Kernel32Ex.class, W32APIOptions.DEFAULT_OPTIONS);
//		}
//        // 控制声音频率  
//        int freqs[] = { 523, 587, 659, 698, 784, 880, 998, 1047, 998, 880, 784,  
//                698, 659, 587, 523 };  
//        // 控制声音时长  
//        int dwruration = 300;  
//        for (int freq : freqs) {  
//        	kernel32.Beep(freq, dwruration);  
//        } 
		
		 System.out.print("\07");
	}

	public interface Kernel32Ex extends Kernel32 {
		long Beep(long dwFreq, long dwDuration);
	}

}
