/*
 * Copyright (c) 2016-9999, ShiXiaoyong. All rights reserved.
 */
package com.simon.commonsall.utils;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.sun.jna.Platform;

/**
 * <pre>
 * UsageUtils
 * </pre>
 * 
 * @author ShiXiaoyong
 * @date 2017年2月26日
 * @version 1.0
 */
public class UsageUtils {
	
	static int MB = 1024 * 1024;
	
	static int GB = 1024 * 1024 * 1024;
	
	public static void main(String[] args) {
		Usage u = UsageUtils.getUsage();
		System.out.println("**** Sizes in Mega Bytes ****");
		System.out.println("PHYSICAL MEMORY DETAILS");
		System.out.println("total physical memory : " + u.physicalMemorySize + "MB ");
		System.out.println("total free physical memory : " + u.physicalfreeMemorySize + "MB");
		/* DISC SPACE DETAILS */
		System.out.println("**** Sizes in Giga Bytes ****");
		System.out.println("DISC SPACE DETAILS");
		System.out.println("Total Space in drive : " + u.totalCapacity + "GB");
		System.out.println("Free Space in drive : " + u.freePartitionSpace + "GB");
		
		/* CPU DETAILS */
		System.out.println("**** CPU DETAILS ****");
		System.out.println("CPU load: " + u.freePartitionSpace + "%");
	}

	public static Usage getUsage() {
		Usage u = new Usage();
		cpu(u);
		disk(u);
		if (Platform.isWindows()) {
			memoryWindows(u);
		}else if(Platform.isLinux()){
			memoryLinux(u);
			uptime(u);
		}
		return u;
	}
	
	static void disk(Usage u){
		File diskPartition = new File("/");
		u.totalCapacity = diskPartition.getTotalSpace() / GB;
		u.freePartitionSpace = diskPartition.getFreeSpace() / GB;
	}
	
	@SuppressWarnings("restriction")
	static void cpu(Usage u){
		com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean) java.lang.management.ManagementFactory.getOperatingSystemMXBean();
		u.cpuLoad = (int) (os.getSystemCpuLoad()*100);
		u.availableProcessors =  Runtime.getRuntime().availableProcessors();
	}
	
	@SuppressWarnings("restriction")
	static void memoryWindows(Usage u){
		com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean) java.lang.management.ManagementFactory.getOperatingSystemMXBean();
		u.physicalMemorySize = os.getTotalPhysicalMemorySize() / MB;
		u.physicalfreeMemorySize = os.getFreePhysicalMemorySize() / MB;
	}
	
	static void memoryLinux(Usage u){
		InputStream inputStream = null;
		try {
			//先判断free命令的版本
			Process exec = Runtime.getRuntime().exec("free -V");
			inputStream = exec.getInputStream();
			List<String> readLines = IOUtils.readLines(inputStream);
			int version = 1;//1:3.2,2:3.3
			if (CollectionUtils.isNotEmpty(readLines)) {
				String[] split = StringUtils.split(readLines.get(0));
				String ver = split[split.length-1];
				if (StringUtils.startsWith(ver, "3.2")) {
					version = 1;
				}else if(StringUtils.startsWith(ver, "3.3")) {
					version = 2;
				}
			}
			IOUtils.closeQuietly(inputStream);
			
			if (1==version) {
				exec = Runtime.getRuntime().exec("free -m");
				inputStream = exec.getInputStream();
				readLines = IOUtils.readLines(inputStream);
				if (CollectionUtils.isNotEmpty(readLines) && readLines.size()>3) {
					String string = readLines.get(2);
					string = StringUtils.substringAfter(string, ":");
					String[] split = StringUtils.split(string);
					if (ArrayUtils.isNotEmpty(split)&& split.length>=2) {
						u.physicalfreeMemorySize =Long.parseLong(split[1]);
						u.physicalMemorySize = Long.parseLong(split[0]) + u.physicalfreeMemorySize;
					}
				}
			}else if(2==version) {
				exec = Runtime.getRuntime().exec("free -m");
				inputStream = exec.getInputStream();
				readLines = IOUtils.readLines(inputStream);
				if (CollectionUtils.isNotEmpty(readLines) && readLines.size()>2) {
					String string = readLines.get(1);
					string = StringUtils.substringAfter(string, ":");
					String[] split = StringUtils.split(string);
					if (ArrayUtils.isNotEmpty(split)&& split.length>=6) {
						u.physicalfreeMemorySize =Long.parseLong(split[5]);
						u.physicalMemorySize = Long.parseLong(split[0]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			IOUtils.closeQuietly(inputStream);
		}
	}
	
	static void uptime(Usage u){
		InputStream inputStream = null;
		try {
			Process exec = Runtime.getRuntime().exec("cat /proc/uptime");
			inputStream = exec.getInputStream();
			List<String> readLines = IOUtils.readLines(inputStream,StandardCharsets.UTF_8);
			if (CollectionUtils.isNotEmpty(readLines)) {
				String string = readLines.get(0);
				String[] split = StringUtils.split(string);
				if (ArrayUtils.isNotEmpty(split)) {
					u.systemUpTime = (long) (NumberUtils.toFloat(split[0]) * 1000);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
	}
	

	public static class Usage {

		/**
		 * 物理内存大小(MB)
		 * 
		 * @author ShiXiaoyong
		 * @date 2017年2月26日
		 */
		public long physicalMemorySize;

		/**
		 * 剩余物理内存大小(MB)
		 * 
		 * @author ShiXiaoyong
		 * @date 2017年2月26日
		 */
		public long physicalfreeMemorySize;

		/**
		 * 磁盘空间(GB)
		 * 
		 * @author ShiXiaoyong
		 * @date 2017年2月26日
		 */
		public long totalCapacity;

		/**
		 * 剩余磁盘空间(GB)
		 * 
		 * @author ShiXiaoyong
		 * @date 2017年2月26日
		 */
		public long freePartitionSpace;
		
		/**
		 * 处理器占用百分比
		 * @author ShiXiaoyong
		 * @date   2017年2月27日
		 */
		public int cpuLoad;
		
		/**
		 * 系统已运行时间(ms)
		 * 
		 * @author ShiXiaoyong
		 * @date 2017年3月20日
		 */
		public long systemUpTime;
		
		/**
		 * CPU可用内核数
		 * @author ShiXiaoyong
		 * @date   2017年3月22日
		 */
		public int availableProcessors;

	}
}
