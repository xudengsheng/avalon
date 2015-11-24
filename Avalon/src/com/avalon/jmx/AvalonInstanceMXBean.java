package com.avalon.jmx;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.avalon.setting.AvalonServerMode;

import javassist.CannotCompileException;
import javassist.NotFoundException;

public interface AvalonInstanceMXBean {

	String getName();

	void stopEngine();

	AvalonServerMode getServerMode();

	int GateServreNum();

	int GameServerNum();
	
	void reloadJar(String fileName) 
			throws FileNotFoundException, IOException;

	void reloadClazz(String fileName, String clazzName) 
			throws FileNotFoundException, IOException;

   void reloadMethod(String clazz, String methodName, String context)
			throws NotFoundException, CannotCompileException, IOException;
}
