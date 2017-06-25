package com.fet.ice.simpleETL.transform;

import java.sql.Connection;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import com.fet.ice.VO.NormalizedVO;

public class TransformedRecord {


	private Date ETL_DT;
	private String src_Schema;
	private String dest_Schema;
	private String dest_SQL;
	private List<NormalizedVO> lsRecords;
	
	

	public TransformedRecord(Date eTL_DT, String src_Schema, String dest_Schema, List lsRecords) {
		super();

		this.ETL_DT = new java.sql.Date((new java.util.Date()).getTime());
		this.src_Schema = src_Schema;
		this.dest_Schema = dest_Schema;
		this.lsRecords = lsRecords;
		
		// construct dest_SQL from DB Table - Mapping
		//dest_SQL = constrcutDestSQL();
	}

	public TransformedRecord(Date eTL_DT, String src_Schema, String dest_Schema) {
		super();

		this.ETL_DT = new java.sql.Date((new java.util.Date()).getTime());
		this.src_Schema = src_Schema;
		this.dest_Schema = dest_Schema;

		if (lsRecords == null) {
			lsRecords = new LinkedList();
		}
	}
	


	/**
	 * @return the eTL_DT
	 */
	public Date getETL_DT() {
		return ETL_DT;
	}

	/**
	 * @param eTL_DT
	 *            the eTL_DT to set
	 */
	public void setETL_DT(Date eTL_DT) {
		ETL_DT = eTL_DT;
	}

	/**
	 * @return the src_Schema
	 */
	public String getSrc_Schema() {
		return src_Schema;
	}

	/**
	 * @param src_Schema
	 *            the src_Schema to set
	 */
	public void setSrc_Schema(String src_Schema) {
		this.src_Schema = src_Schema;
	}

	/**
	 * @return the dest_Schema
	 */
	public String getDest_Schema() {
		return dest_Schema;
	}

	/**
	 * @param dest_Schema
	 *            the dest_Schema to set
	 */
	public void setDest_Schema(String dest_Schema) {
		this.dest_Schema = dest_Schema;
	}


	/**
	 * @return the lsRecords
	 */
	public List<NormalizedVO> getLsRecords() {
		return lsRecords;
	}

	/**
	 * @param lsRecords
	 *            the lsRecords to set
	 */
	public void setLsRecords(List<NormalizedVO> lsRecords) {
		this.lsRecords = lsRecords;
	}

}
