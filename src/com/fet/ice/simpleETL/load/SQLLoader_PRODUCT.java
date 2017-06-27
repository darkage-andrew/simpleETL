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
import com.fet.ice.simpleETL.entity.COMMON_ATTRIBUTE;
import com.fet.ice.simpleETL.entity.COMMON_ENTITY;
import com.fet.ice.simpleETL.entity.COMMON_RELATIONITEM;
import com.fet.ice.simpleETL.entity.PRODUCT_DATA;
import com.fet.ice.simpleETL.exception.jobException;
import com.fet.ice.simpleETL.job.threadJob;
import com.fet.ice.simpleETL.util.DBMgr;
import com.fet.ice.simpleETL.util.PrintStackTraceUtil;
import com.fet.ice.simpleETL.util.PropertyUtil;

// after data transformed, write to destination DB
public class SQLLoader_PRODUCT {

	// constants
	protected static final int SYNC_INCREMENTAL = 0;
	protected static final int SYNC_WHOLEBUNCH = 1;

	protected static final int CONNECTION_URL_ERROR = 0;
	protected static final int CONNECTION_DRIVER_ERROR = 1;
	protected static final int CONNECTION_FAILED = 2;
	protected static final int CONNECTION_DISPOSE_FAILED = 3;

	protected static final String JOB_RESULT_SUCCESS = "S";
	protected static final String JOB_RESULT_FAILED = "F";

	private static PropertyUtil propUtil;
	private Connection destConn;
	private PreparedStatement ora_st;
	private PreparedStatement ora_st_attr;
	private PreparedStatement ora_st_item_relation;
	

	// Data sync required
	protected int iSrcDataCount;
	private Logger logger;
	private PrintStackTraceUtil printStackTraceUtil;

	// log related
	protected Timestamp dtPerformed;
	protected Timestamp dtFinished;
	private DBMgr dbMgr;

	
	public SQLLoader_PRODUCT(Logger logger) {
		this.logger = logger;
		this.printStackTraceUtil = PrintStackTraceUtil.getPrintStackTraceUtil();

		propUtil = PropertyUtil.getPropertyUtil(logger);
	}

	public void perfomSync(PRODUCT_DATA oProductData) {

		dbMgr = DBMgr.getDBMgr(logger);
		
/*		List<COMMON_ENTITY> lsSubs = oProductData.getLsSubs();
		writeToDB(lsSubs, "SUBS");

		List<COMMON_ENTITY> lsL3PGS = oProductData.getLsL3PGs();
		writeToDB(lsL3PGS, "L3PGS");

		List<COMMON_ENTITY> lsL4PGS = oProductData.getLsL4PGs();
		writeToDB(lsL4PGS, "L4PGS");

		List<COMMON_ENTITY> lsPRODUCTOFFERS = oProductData.getLsPRODUCTOFFERs();
		writeToDB(lsPRODUCTOFFERS, "PRODUCTOFFERS");

		List<COMMON_ENTITY> lsBFSS = oProductData.getLsBFSs();
		writeToDB(lsBFSS, "BFSS");

		List<COMMON_ENTITY> lsCFSS = oProductData.getLsCFSs();
		writeToDB(lsCFSS, "CFSS");
		
		List<COMMON_ENTITY> lsSERVICES = oProductData.getLsServices();
		writeToDB(lsSERVICES, "SERVICES");
	
		List<COMMON_ENTITY> lsRFSS = oProductData.getLsRFSs();
		writeToDB(lsRFSS, "RFSS");	
*/	}
	
	
	
	/**
	 * @param lsEntity
	 * @param sName
	 */
	private void writeToDB(List<COMMON_ENTITY> lsEntity, String sName) {

		int iSrcCount = 0;
		int iLoadCount = 0;
		int iResult = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		recordStartTime();

		try {
			// perform DB sync

			if (lsEntity != null && lsEntity.size() >= 0) {

				iSrcCount = lsEntity.size();

				// get DB connection
				destConn = dbMgr.createDBConnection();
				ora_st = destConn.prepareStatement((String) propUtil.getProperty("COH_ITEM_SQL"));
				ora_st_attr = destConn.prepareStatement((String) propUtil.getProperty("COH_ATTRIBUTE_SQL"));
				ora_st_item_relation = destConn.prepareStatement((String) propUtil.getProperty("ITEM_RELATION_SQL"));

				for (int i = 0; i < lsEntity.size(); i++) {
					COMMON_ENTITY oEntity = lsEntity.get(i);

					// INSERT INTO SIMPLEETL.COH_ITEM (ITEM_CODE, ITEM_TYPE,
					// ITEM_NAME, START_DATE, END_DATE, ORDERABLE, STATUS)
					// VALUES (?,?,?,?,?,?,?);
					ora_st.setString(1, oEntity.getITEMCODE());
					ora_st.setString(2, oEntity.getITEMTYPE());
					ora_st.setString(3, oEntity.getNAME());
					ora_st.setString(4, oEntity.getSTARTDATE());
					ora_st.setString(5, oEntity.getENDDATE());
					ora_st.setString(6, oEntity.getORDERABLE());
					ora_st.setString(7, oEntity.getSTATUS());

					iResult = ora_st.executeUpdate();
					
					
					//write STAGING_COH_RELATION_ITEM
					List<COMMON_RELATIONITEM> lsItemRelations = oEntity.getLsItemRelations();
					for(int j = 0; j < lsItemRelations.size(); j++)
					{
						COMMON_RELATIONITEM oRelationItem = lsItemRelations.get(j);

						//STAGING_ITEM_RELATION_SQL=INSERT INTO SIMPLEETL.STAGING_COH_RELATION_ITEM (PARENT_ITEM_CODE, ITEM_RELATION_TARGET, ITEM_RELATION_CODE, STATUS, START_DATE, END_DATE) 
						//VALUES (?,?,?,?,?,?)
						ora_st_item_relation.setString(1, oEntity.getITEMCODE());
						ora_st_item_relation.setString(2, oRelationItem.getITEMRELATIONTARGET());
						ora_st_item_relation.setString(3, oRelationItem.getITEMRELATIONCODE());
						ora_st_item_relation.setString(4, oRelationItem.getSTATUS());
						ora_st_item_relation.setString(5, oRelationItem.getSTARTDATE());
						ora_st_item_relation.setString(6, oRelationItem.getENDDATE());
												
						ora_st_item_relation.executeUpdate();
					}
					
					//write COH_ATTRIBUTE
					List<COMMON_ATTRIBUTE> lsAttributes = oEntity.getLsAttributes();
					
					for(int k =0; k < lsAttributes.size(); k++){						
						COMMON_ATTRIBUTE oAttribute = (COMMON_ATTRIBUTE)lsAttributes.get(k); 
						
						//STAGING_COH_ATTRIBUTE=INSERT INTO SIMPLEETL.STAGING_COH_ATTRIBUTE (PARENT_ITEM_CODE, ITEM_ATTRIBUTE_CODE, ATTRIBUTE_CODE, ITEM_RELATION_CODE, ATTR_TYPE, STATUS, ATTR_NAME, START_DATE, END_DATE, DEFAULT_VALUE, ASSOCIATION_TYPE) 
						//VALUES (?,?,?,?,?,?,?,?,?,?,?)	
							
/*						logger.debug("oAttribute.parentitemcode=" + oEntity.getITEMCODE());
						logger.debug("oAttribute.itemattributecode=" + oAttribute.getITEMATTRIBUTECODE());
						logger.debug("oAttribute.attributecode=" + oAttribute.getATTRIBUTECODE());
						logger.debug("oAttribute.itemrelationcode=" + oAttribute.getITEMRELATIONCODE());
						logger.debug("oAttribute.type=" + oAttribute.getTYPE());
						logger.debug("oAttribute.status=" + oAttribute.getSTATUS());
						logger.debug("oAttribute.name=" + oAttribute.getNAME());
						logger.debug("oAttribute.startdate=" + oAttribute.getSTARTDATE());
						logger.debug("oAttribute.enddate=" + oAttribute.getENDDATE());
						logger.debug("oAttribute.defaultvalue=" + oAttribute.getDEFAULTVALUE());
						logger.debug("oAttribute.associatetype=" + oAttribute.getASSOCIATIONTYPE());									
*/						
						ora_st_attr.setString(1, oEntity.getITEMCODE());
						ora_st_attr.setString(2, oAttribute.getITEMATTRIBUTECODE());
						ora_st_attr.setString(3, oAttribute.getATTRIBUTECODE());
						ora_st_attr.setString(4, oAttribute.getITEMRELATIONCODE());
						ora_st_attr.setString(5, oAttribute.getTYPE());
						ora_st_attr.setString(6, oAttribute.getSTATUS());
						ora_st_attr.setString(7, oAttribute.getNAME());
						ora_st_attr.setString(8, oAttribute.getSTARTDATE());
						ora_st_attr.setString(9, oAttribute.getENDDATE());
						ora_st_attr.setString(10, oAttribute.getDEFAULTVALUE());
						ora_st_attr.setString(11, oAttribute.getASSOCIATIONTYPE());
						
						ora_st_attr.executeUpdate();
					}
					
					logger.debug("iResult("+i+")=" + iResult);
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
				
				
				if (ora_st_item_relation != null) {
					ora_st_item_relation.close();
					ora_st_item_relation = null;
				}


				if (ora_st_attr != null) {
					ora_st_attr.close();
					ora_st_attr = null;
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
			logger.error("[SQLLoader_PRODUCT] SQLException: " + this.printStackTraceUtil.printStackTrace(se));
			logger.debug("[SQLLoader_PRODUCT] SQLException:" + se.getMessage());

			try {
				// rollback the whole transaction in Src Connection,
				destConn.rollback();
			} catch (SQLException se4) {
				logger.error("[SQLLoader_PRODUCT] SQLException4: " + this.printStackTraceUtil.printStackTrace(se4));
				logger.debug("[SQLLoader_PRODUCT] SQLException4: " + se4.getMessage());
			}
		}

		finally {
			// record job stop time
			recordStopTime();

			logger.info("================ SQLLoader_PRODUCT (" + sName + ") ==================");
			logger.info("start date: " + sdf.format(this.dtPerformed));
			logger.info("read source: " + iSrcCount + " records");
			logger.info("write destination: " + iLoadCount + " records");
			logger.info("finished date: " + sdf.format(this.dtFinished));
			logger.info("=======================================================");

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
