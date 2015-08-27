package com.avalon.util;

import java.io.File;
import java.util.List;

import jodd.io.findfile.FindFile;

import com.avalon.exception.FileNotExitException;
import com.google.common.collect.Lists;

public final class FileUtil {
	/**
	 * 查找文件
	 * 
	 * @param searchPath
	 *            搜索路径
	 * @param IncludeDirs
	 *            是否包含子文件夹
	 * @return
	 */
	public static List<File> scanPath(String searchPath, boolean IncludeDirs) {
		List<File> result = Lists.newArrayList();
		@SuppressWarnings("rawtypes")
		FindFile<?> ff = new FindFile().setRecursive(true).setIncludeDirs(IncludeDirs).searchPath(searchPath);
		File f;
		while ((f = ff.nextFile()) != null) {
			result.add(f);
		}
		return result;
	}

	/**
	 * 查找文件(不包含子文件夹)
	 * 
	 * @param searchPath
	 *            搜索路径
	 * @return
	 */
	public static List<File> scanPath(String searchPath) {
		return scanPath(searchPath, false);
	}

	/**
	 * 查找文件在指定目录下
	 * 
	 * @param searchPath
	 * @param fileName
	 * @return
	 */
	public static File scanFileByPath(String searchPath, String fileName) throws FileNotExitException {
		List<File> scanPath = scanPath(searchPath, false);
		for (File file : scanPath) {
			if (file.getName().equals(fileName)) { return file; }
		}
		throw new FileNotExitException();
	}
}
