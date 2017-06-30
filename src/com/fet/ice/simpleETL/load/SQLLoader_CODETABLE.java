package com.fet.ice.simpleETL.load;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.apache.logging.log4j.Logger;


import com.fet.ice.simpleETL.entity.CODE_TABLE;
import com.fet.ice.simpleETL.exception.jobException;
import com.fet.ice.simpleETL.job.threadJob;
import com.fet.ice.simpleETL.util.DBMgr;
import com.fet.ice.simpleETL.util.PrintStackTraceUtil;
import com.fet.ice.simpleETL.util.PropertyUtil;

// after data transformed, write to destination DB
public class SQLLoader_CODETABLE {

	// constants
	protected final static int SYNC_INCREMENTAL = 0;
	protected final static int SYNC_WHOLEBUNCH = 1;

	protected final static int CONNECTION_URL_ERROR = 0;
	protected final static int CONNECTION_DRIVER_ERROR = 1;
	protected final static int CONNECTION_FAILED = 2;
	protected final static int CONNECTION_DISPOSE_FAILED = 3;

	protected final static String JOB_RESULT_SUCCESS = "S";
	protected final static String JOB_RESULT_FAILED = "F";

	// protected int iNumCommit = 1000;
	private static PropertyUtil propUtil;
	private Connection destConn;
	private PreparedStatement ora_st;

	// Data sync required
	protected int iSrcDataCount;
	private Logger logger;
	private boolean bFinished = false;
	private PrintStackTraceUtil printStackTraceUtil;

	// log related
	protected Timestamp dtPerformed;
	protected Timestamp dtFinished;

	public SQLLoader_CODETABLE(Logger logger) {
		this.logger = logger;
		this.printStackTraceUtil = PrintStackTraceUtil.getPrintStackTraceUtil();

		propUtil = PropertyUtil.getPropertyUtil(logger);
	}

	public void perfomSync(List lsCodeTable) {

		int iSrcCount = 0;
		int iLoadCount = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		recordStartTime();

		try {
			// perform DB sync
			if (lsCodeTable != null && lsCodeTable.size() >= 0) {

				iSrcCount = lsCodeTable.size();

				// get DB connection
				DBMgr dbMgr = DBMgr.getDBMgr(logger);
				destConn = dbMgr.createDBConnection();
				ora_st = destConn.prepareStatement((String) propUtil.getProperty("CODETABLE_SQL"));

				for (int i = 0; i < lsCodeTable.size(); i++) {
					CODE_TABLE oCodeTable = (CODE_TABLE) lsCodeTable.get(i);

					// INSERT INTO SIMPLEETL.CODETABLE
					// (ATTRIBUTECODE,CODETABLENAME,NAME,DESCRIPTION,STARTDATE,ENDDATE)
					// VALUES (?,?,?,?,?,?)
					ora_st.setString(1, oCodeTable.getATTRIBUTECODE());
					ora_st.setString(2, oCodeTable.getCODETABLENAME());
					ora_st.setString(3, oCodeTable.getNAME());
					ora_st.setString(4, oCodeTable.getDESCRIPTION());
					ora_st.setString(5, oCodeTable.getSTARTDATE());
					ora_st.setString(6, oCodeTable.getENDDATE());

					int iResult = ora_st.executeUpdate();
					iLoadCount += iResult;
				}

				if (!dbMgr.isAutoCommit()) {
					destConn.commit();
				}
			}

			// dispose statements and connection
			try {
				// dispose statements
				if (ora_st != null) {
					ora_st.close();
					ora_st = null;
				}

				// dispose connection
				if (this.destConn != null) {
					destConn.close();
					destConn = null;
				}

			} catch (SQLException sqle) {
				logger.debug(sqle.getMessage());
			}

		} // end try
		catch (SQLException se) {
			logger.error("[SQLLoader_CODETABLE] SQLException: " + this.printStackTraceUtil.printStackTrace(se));
			logger.debug("[SQLLoader_CODETABLE] SQLException:" + se.getMessage());

			try {
				// rollback the whole transaction in Src Connection,
				destConn.rollback();
			} catch (SQLException se4) {
				logger.error("[SQLLoader_CODETABLE] SQLException4: " + this.printStackTraceUtil.printStackTrace(se4));
				logger.debug("[SQLLoader_CODETABLE] SQLException4: " + se4.getMessage());
			}
		}

		finally {
			// record job stop time
			recordStopTime();

			logger.info("================ SQLLoader_CODETABLE ==================");
			logger.info("start date: " + sdf.format(this.dtPerformed));
			logger.info("read source: " + iSrcCount + " records");
			logger.info("write destination: " + iLoadCount + " records");
			logger.info("finished date: " + sdf.format(this.dtFinished));
			logger.info("=======================================================");

			this.bFinished = true;
		}

	}

	/**
	 * 關閉DB連線
	 */
	protected void disposeDBConnections() {
		if (destConn != null) {
			try {
				destConn.close();
				destConn = null;
			} catch (SQLException se) {
				logger.debug("SQLException: " + this.printStackTraceUtil.printStackTrace(se));
			}
		}

	}

	/**
	 * 記錄開始時間
	 */
	protected void recordStartTime() {
		this.dtPerformed = new Timestamp(new java.util.Date().getTime());
	}

	/**
	 * 記錄停止時間
	 */
	protected void recordStopTime() {
		this.dtFinished = new Timestamp(new java.util.Date().getTime());
	}

	/**
	 * 處理exception 發生後的簡訊發送
	 * 
	 * @param iErrCode:
	 *            ERROR CODE
	 * @param sReason:
	 *            ERROR MESSAGE
	 */
	protected void ExceptionHandling(int iErrCode, String sReason) {
		logger.debug(sReason);
	}

}
