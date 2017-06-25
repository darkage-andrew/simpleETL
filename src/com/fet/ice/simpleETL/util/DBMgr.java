package com.fet.ice.simpleETL.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.Logger;

/**
 * @author Andrew Tsai singleton pattern applied for ensuring only one instance
 */
public class DBMgr {

	static PropertyUtil propUtil;
	Logger logger;

	final static int DB_ORACLE = 0;
	// parameter
	boolean bAutoCommit = true;

	private static DBMgr dbMgr = new DBMgr();

	private DBMgr() {
	}

	public static DBMgr getDBMgr(Logger logger) {
		dbMgr.logger = logger;
		propUtil = PropertyUtil.getPropertyUtil(logger);

		return dbMgr;
	}

	
	public void enableAutoCommit()
	{
		this.bAutoCommit= true;
	}
	
	
	public void disableAutoCommit()
	{
		this.bAutoCommit= false;		
	}
	
	public boolean isAutoCommit()
	{
		return this.bAutoCommit;
	}
	
	
	
	
	/**
	 * 取得錯誤訊息
	 * 
	 * @param ex
	 *            Throwable
	 * @return 回傳拋出異常資訊的字串
	 */
	private String printStackTrace(Throwable ex) {
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
	public Connection createDBConnection() {
		Connection conn = null;

		String sConnString = propUtil.getProperty("DESTCONNSTR");
		String sUser = propUtil.getProperty("DESTACCOUNT");
		String sPW = propUtil.getProperty("DESTPASSWORD");

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(sConnString, sUser, sPW);
			conn.setAutoCommit(bAutoCommit);
		} catch (ClassNotFoundException cnfe) {
			logger.debug("DBMgr.createDBConnection(): ClassNotFoundException: " + printStackTrace(cnfe));
		} catch (SQLException se) {
			logger.debug("DBMgr.createDBConnection(): SQLException: " + printStackTrace(se));
		}
		return conn;
	}

}
