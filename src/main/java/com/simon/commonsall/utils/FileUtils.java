/*  
 * Copyright (c) 2014, ShiXiaoyong. All rights reserved. 
 */
package com.simon.commonsall.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/** 
 * <pre>
 * FileUtils 
 * </pre>
 * 
 * @author Shixy 
 * @date   2015年12月19日
 * @since  1.0 
 */
public class FileUtils {
	
	/**
	 * 支持去除注释行
	 * @author ShiXiaoyong
	 * @date   2017年6月2日
	 * @param filePath
	 * @param encoding
	 * @param lineFilter
	 * @return
	 * @throws IOException
	 */
	public static List<String> readLines(String filePath, String encoding,String[] lineFilter) throws IOException {
		List<String> list = new ArrayList<String>();
		File file = new File(filePath);
		if (file.exists()) {
			List<String> lines = org.apache.commons.io.FileUtils.readLines(file, encoding);
			for (String line : lines) {
				if (ArrayUtils.isEmpty(lineFilter) || !StringUtils.startsWithAny(line, lineFilter)) {
					list.add(line);
				}
			}
		}
		
		return list;
	}
	
}
