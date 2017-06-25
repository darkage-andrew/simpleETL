package com.fet.ice.simpleETL.entity;

import java.util.List;

public class COMMON_ENTITY {

	// for PRODUCT_DATA
	private static final String SUB = "SUB";
	private static final String L3PG = "L3PG";
	private static final String L4PG = "L4PG";
	private static final String PRODUCT_OFFER = "PRODUCTOFFER";
	private static final String BFS = "BFS";
	private static final String CFS = "CFS";
	private static final String SERVICE = "SERVICE";
	private static final String RFS = "RFS";
	
	//for PROMOTION_DATA
	private static final String PROMOTION_GROUP = "PROMOTION_GROUP";
	private static final String PROMOTION_ACTIVITY = "PROMOTION_ACTIVITY";
	private static final String PROMOTION = "PROMOTION";
	private static final String DEVICE = "DEVICE";
	

	private String DATATYPE;// eg. SUB, L3PG, L4PG...etc
	private String ITEMCODE;
	private String ITEMTYPE;
	private String NAME;
	private String STARTDATE;
	private String ENDDATE;
	private String ORDERABLE;
	private String STATUS;
	private List<COMMON_RELATIONITEM> lsItemRelations;
	private List<COMMON_ATTRIBUTE> lsAttributes;


	
	public COMMON_ENTITY(String dataType) {
		// TODO Auto-generated constructor stub
		this.DATATYPE = dataType;
	}

	/**
	 * @return the dATATYPE
	 */
	public String getDATATYPE() {
		return DATATYPE;
	}

	/**
	 * @param dATATYPE
	 *            the dATATYPE to set
	 */
	public void setDATATYPE(String dATATYPE) {
		DATATYPE = dATATYPE;
	}

	/**
	 * @return the iTEMCODE
	 */
	public String getITEMCODE() {
		return ITEMCODE;
	}

	/**
	 * @param iTEMCODE
	 *            the iTEMCODE to set
	 */
	public void setITEMCODE(String iTEMCODE) {
		ITEMCODE = iTEMCODE;
	}

	/**
	 * @return the iTEMTYPE
	 */
	public String getITEMTYPE() {
		return ITEMTYPE;
	}

	/**
	 * @param iTEMTYPE
	 *            the iTEMTYPE to set
	 */
	public void setITEMTYPE(String iTEMTYPE) {
		ITEMTYPE = iTEMTYPE;
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
	 * @return the oRDERABLE
	 */
	public String getORDERABLE() {
		return ORDERABLE;
	}

	/**
	 * @param oRDERABLE
	 *            the oRDERABLE to set
	 */
	public void setORDERABLE(String oRDERABLE) {
		ORDERABLE = oRDERABLE;
	}

	/**
	 * @return the sTATUS
	 */
	public String getSTATUS() {
		return STATUS;
	}

	/**
	 * @param sTATUS
	 *            the sTATUS to set
	 */
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	/**
	 * @return the lsItemRelations
	 */
	public List<COMMON_RELATIONITEM> getLsItemRelations() {
		return lsItemRelations;
	}

	/**
	 * @param lsItemRelations
	 *            the lsItemRelations to set
	 */
	public void setLsItemRelations(List<COMMON_RELATIONITEM> lsItemRelations) {
		this.lsItemRelations = lsItemRelations;
	}

	/**
	 * @return the lsAttributes
	 */
	public List<COMMON_ATTRIBUTE> getLsAttributes() {
		return lsAttributes;
	}

	/**
	 * @param lsAttributes
	 *            the lsAttributes to set
	 */
	public void setLsAttributes(List<COMMON_ATTRIBUTE> lsAttributes) {
		this.lsAttributes = lsAttributes;
	}

}
