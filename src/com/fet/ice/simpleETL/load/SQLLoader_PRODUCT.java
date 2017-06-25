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
import com.fet.ice.simpleETL.entity.PRODUCT_DATA;
import com.fet.ice.simpleETL.exception.jobException;
import com.fet.ice.simpleETL.job.threadJob;

// after data transformed, write to destination DB
public class SQLLoader_PRODUCT {

	// constants
	protected final static int SYNC_INCREMENTAL = 0;
	protected final static int SYNC_WHOLEBUNCH = 1;

	protected final static int CONNECTION_URL_ERROR = 0;
	protected final static int CONNECTION_DRIVER_ERROR = 1;
	protected final static int CONNECTION_FAILED = 2;
	protected final static int CONNECTION_DISPOSE_FAILED = 3;

	protected final static int JOB_STARTED = 11;
	protected final static int JOB_NOT_STARTED = 12;
	protected final static int JOB_PERFORMED_FAILED = 13;

	protected final static String JOB_RESULT_SUCCESS = "S";
	protected final static String JOB_RESULT_FAILED = "F";

	protected final static int DB_ORACLE = 0;
	// parameter
	protected boolean bAutoCommit = false;
	protected int iNumCommit = 1000;

	// destination
	protected String destUrl;
	protected String destDBUser;
	protected String destDBPwd;
	protected Connection destConn;

	protected String jobName;

	// Data sync required
	protected int iSrcDataCount;
	private Logger logger;
	private boolean bFinished = false;

	// log related
	protected Timestamp dtPerformed;
	protected Timestamp dtFinished;

	public SQLLoader_PRODUCT(Logger logger) {
		this.logger = logger;
		logger.debug("executing simpleETL-SQLLoader_PRODUCT");
	}

	public void perfomSync(PRODUCT_DATA oProductData) {

		int iSrcCount = 0;
		int iDestCount = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		try {
			Date dtSync = new Date();
			String dtSync_string = sdf.format(dtSync);

			// perform DB sync
			Statement ora_st = destConn.createStatement();
			// transform to sql statement and write to DB ---------------------

			// write ends here ------------------------------------------------

			logger.debug("after perfomSync(): updated count=" + iDestCount);

			// commit connections
			destConn.commit();

			// record job stop time
			recordStopTime();

			if (ora_st != null) {
				ora_st.close();
				ora_st = null;
			}

		} // end try
		catch (SQLException se) {
			logger.error("[" + jobName + "] SQLException: " + printStackTrace(se));
			logger.debug("[" + jobName + "] SQLException:" + se.getMessage());

			try {
				// rollback the whole transaction in Src Connection,
				destConn.rollback();
			} catch (SQLException se4) {
				logger.error("[" + this.jobName + "] SQLException4: " + printStackTrace(se4));
				logger.debug("[" + jobName + "] SQLException4: " + se4.getMessage());
			}

			this.recordStopTime();
		}

		finally {
			logger.info("================ " + this.jobName + " =================");
			logger.info("start date: " + sdf.format(this.dtPerformed));
			logger.info("read source: " + iSrcCount + " records");
			logger.info("write destination: " + iDestCount + " records");
			logger.info("finished date: " + sdf.format(this.dtFinished));
			logger.info("=======================================================");

			this.bFinished = true;
		}

	}

	/**
	 * 設定目的DB連結參數
	 * 
	 * @param ip:
	 *            DB server ip
	 * @param port:
	 *            port
	 * @param sid:
	 *            oracle SID
	 * @param sUser:
	 *            USER NAME
	 * @param sPW:
	 *            PASSWORD
	 * @param schema:
	 *            SCHEMA NAME
	 */
	public void setDestDBParameters(String sConnStr, String sUser, String sPW) {
		destUrl = sConnStr;
		destDBUser = sUser;
		destDBPwd = sPW;
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

	/**
	 * 建立 DB連線
	 * 
	 * @param sUrl:
	 *            URL
	 * @param sUser:
	 *            USER NAME
	 * @param sPW:
	 *            PASSWORD
	 * @return: Connection
	 */
	protected Connection createDBConnection(int iDBType, String sUrl, String sUser, String sPW) {
		Connection conn = null;

		try {
			switch (iDBType) {
			case DB_ORACLE:
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(sUrl, sUser, sPW);
				break;
			}

			conn.setAutoCommit(bAutoCommit);
		} catch (ClassNotFoundException cnfe) {
			logger.debug("[" + jobName + "].createDBConnection() ClassNotFoundException: " + printStackTrace(cnfe));
		} catch (SQLException se) {
			logger.debug("[" + jobName + "].createDBConnection() SQLException: " + printStackTrace(se));
		}
		return conn;
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
				logger.debug("SQLException: " + printStackTrace(se));
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
	 * 子物件繼承且 override 的方法
	 */
	protected void setPerformLog() {

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
