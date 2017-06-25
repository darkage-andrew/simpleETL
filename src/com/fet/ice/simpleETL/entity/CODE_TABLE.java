package com.fet.ice.simpleETL.entity;

import java.util.ArrayList;
import java.util.List;

import com.fet.ice.simpleETL.transform.TransformedRecord;

public class CODE_TABLE {

	private String ATTRIBUTECODE;
	private String CODETABLENAME;
	private String NAME;
	private String DESCRIPTION;
	private String STARTDATE;
	private String ENDDATE;

	private List<CODETABLE_VALUEITEM> lsVALUEITEMS;

	/**
	 * 
	 */
	public CODE_TABLE() {

		// TODO Auto-generated constructor stub
	}

	/**
	 * @param aTTRIBUTECODE
	 * @param cODETABLENAME
	 * @param nAME
	 * @param dESCRIPTION
	 * @param sTARTDATE
	 * @param eNDDATE
	 */
	public CODE_TABLE(String aTTRIBUTECODE, String cODETABLENAME, String nAME, String dESCRIPTION, String sTARTDATE,
			String eNDDATE) {
		super();
		ATTRIBUTECODE = aTTRIBUTECODE;
		CODETABLENAME = cODETABLENAME;
		NAME = nAME;
		DESCRIPTION = dESCRIPTION;
		STARTDATE = sTARTDATE;
		ENDDATE = eNDDATE;
		lsVALUEITEMS = new ArrayList<CODETABLE_VALUEITEM>();
	}

	/**
	 * @param aTTRIBUTECODE
	 * @param cODETABLENAME
	 * @param nAME
	 * @param dESCRIPTION
	 * @param sTARTDATE
	 * @param eNDDATE
	 * @param lsVALUEITEMS
	 */
	public CODE_TABLE(String aTTRIBUTECODE, String cODETABLENAME, String nAME, String dESCRIPTION, String sTARTDATE,
			String eNDDATE, List<CODETABLE_VALUEITEM> lsVALUEITEMS) {
		super();
		ATTRIBUTECODE = aTTRIBUTECODE;
		CODETABLENAME = cODETABLENAME;
		NAME = nAME;
		DESCRIPTION = dESCRIPTION;
		STARTDATE = sTARTDATE;
		ENDDATE = eNDDATE;
		this.lsVALUEITEMS = lsVALUEITEMS;
	}

	/**
	 * @return the aTTRIBUTECODE
	 */
	public String getATTRIBUTECODE() {
		return ATTRIBUTECODE;
	}

	/**
	 * @param aTTRIBUTECODE
	 *            the aTTRIBUTECODE to set
	 */
	public void setATTRIBUTECODE(String aTTRIBUTECODE) {
		ATTRIBUTECODE = aTTRIBUTECODE;
	}

	/**
	 * @return the cODETABLENAME
	 */
	public String getCODETABLENAME() {
		return CODETABLENAME;
	}

	/**
	 * @param cODETABLENAME
	 *            the cODETABLENAME to set
	 */
	public void setCODETABLENAME(String cODETABLENAME) {
		CODETABLENAME = cODETABLENAME;
	}

	/**
	 * @return the nAME
	 */
	public String getNAME() {
		return NAME;
	}

	/**
	 * @param nAME
	 *            the nAME to set
	 */
	public void setNAME(String nAME) {
		NAME = nAME;
	}

	/**
	 * @return the dESCRIPTION
	 */
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	/**
	 * @param dESCRIPTION
	 *            the dESCRIPTION to set
	 */
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	/**
	 * @return the sTARTDATE
	 */
	public String getSTARTDATE() {
		return STARTDATE;
	}

	/**
	 * @param sTARTDATE
	 *            the sTARTDATE to set
	 */
	public void setSTARTDATE(String sTARTDATE) {
		STARTDATE = sTARTDATE;
	}

	/**
	 * @return the eNDDATE
	 */
	public String getENDDATE() {
		return ENDDATE;
	}

	/**
	 * @param eNDDATE
	 *            the eNDDATE to set
	 */
	public void setENDDATE(String eNDDATE) {
		ENDDATE = eNDDATE;
	}

	/**
	 * @return the lsVALUEITEMS
	 */
	public List<CODETABLE_VALUEITEM> getLsVALUEITEMS() {
		return lsVALUEITEMS;
	}

	/**
	 * @param lsVALUEITEMS
	 *            the lsVALUEITEMS to set
	 */
	public void setLsVALUEITEMS(List<CODETABLE_VALUEITEM> lsVALUEITEMS) {
		this.lsVALUEITEMS = lsVALUEITEMS;
	}


	public TransformedRecord toTransformRecord()
	{
		//需要想一下
		
		return null;
	}

	/**
	 * @return JSON string
	 */
	public String toJSON() {
		String sJON = new String();

		return sJON;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String sTemp = new String();

		return sTemp;
	}

}
