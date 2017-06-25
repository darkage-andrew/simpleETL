/**
 * 
 */
package com.fet.ice.simpleETL.job;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import org.apache.logging.log4j.*;

/**
 * 2017/06/09 created
 * <p>
 * Title: com.fet.ice.simpleETL.threadJob
 * </p>
 * <p>
 * Description: base object for batch jobs
 * </p>
 * <p>
 * Copyright: (c) Copyright HPE. 2017. All Rights Reserved.
 * </p>
 * $Id: threadJob.java 135 2017-06-09 06:51:30Z andrew $ $Revision: 135 $
 * $Author: andrew $ $Date: 2017-06-09 14:51:30 +0800 (Fri, 09 Jun 2017) $
 */
public class threadJob implements Runnable {

	protected final static int JOB_NONE = 0;
	protected final static int JOB_STARTED = 1;
	protected final static int JOB_ERROR = 2;
	protected final static int JOB_COMPLETED = 3;
	protected final static int JOB_EXTRACTING = 30;
	protected final static int JOB_EXTRACT_COMPLETED = 31;
	protected final static int JOB_TRANSFORMING = 40;
	protected final static int JOB_TRANSFORM_COMPLETED = 41;
	protected final static int JOB_WRITING = 50;
	protected final static int JOB_WRITE_COMPLETED = 51;
	protected final static int JOB_FAILED = 99;

	protected String jobName;

	// log related
	protected Timestamp dtStarted;
	protected Timestamp dtFinished;
	protected Logger logger;

	protected int iStatus = JOB_NONE;

	public threadJob() {
	}

	/**
	 * 
	 */
	public threadJob(String jobName, Logger logger) {
		this.jobName = jobName;
		this.logger = logger;
	}

	/**
	 * 
	 * 
	 * @param ex
	 *            Throwable
	 * @return
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

	/**
	 * 
	 */
	protected void recordStartTime() {
		this.dtStarted = new Timestamp(new java.util.Date().getTime());
	}

	/**
	 * 
	 */
	protected void recordStopTime() {
		this.dtFinished = new Timestamp(new java.util.Date().getTime());
	}

	/**
	 * 
	 */
	protected void setPerformLog() {

	}

	/**
	 * 
	 * @param iErrCode:
	 *            ERROR CODE
	 * @param sReason:
	 *            ERROR MESSAGE
	 */
	protected void ExceptionHandling(int iErrCode, String sReason) {
		logger.debug(sReason);
	}

	/**
	 * 
	 */
	protected void doJob() {
		// leave implementation to child class
		iStatus = this.JOB_STARTED;
	}

	/*
	 * 
	 */
	public void run() {
		// record job start time
		recordStartTime();
		logger.debug(jobName + " starts @" + this.dtStarted);

		// threadjob, performs XML parsing
		doJob();

		recordStopTime();
		logger.debug(jobName + ", stops @" + this.dtFinished + ", total time consumed - "
				+ (dtFinished.getTime() - dtStarted.getTime()) + " ms");
	}

	// implement toString() in Runnable
	public String toString() {
		return this.jobName;
	}

}
