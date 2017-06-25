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
	 * ���o���~�T��
	 * 
	 * @param ex
	 *            Throwable
	 * @return �^�ǩߥX���`��T���r��
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
