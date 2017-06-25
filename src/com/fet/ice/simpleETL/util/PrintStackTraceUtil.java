package com.fet.ice.simpleETL.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.logging.log4j.Logger;

public class PrintStackTraceUtil {

	private static PrintStackTraceUtil printStackTraceUtil = new PrintStackTraceUtil();

	private PrintStackTraceUtil() {
		// TODO Auto-generated constructor stub
	}

	public static PrintStackTraceUtil getPrintStackTraceUtil() {
		return printStackTraceUtil;
	}

	
	/**
	 * 取得錯誤訊息
	 * 
	 * @param ex
	 *            Throwable
	 * @return 回傳拋出異常資訊的字串
	 */
	public String printStackTrace(Throwable ex) {
		StringWriter swriter = new StringWriter();
		PrintWriter pwriter = new PrintWriter(swriter);
		ex.printStackTrace(pwriter);
		String stackTrace = swriter.toString();
		try {
			swriter.close();
			pwriter.close();
		} catch (IOException ioe) {
		}
		return stackTrace;
	}	
}
