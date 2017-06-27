package com.fet.ice.simpleETL.entity;

import java.util.List;

import com.fet.ice.simpleETL.transform.TransformedRecord;

public class PRODUCT_DATA {

	private List<COMMON_ENTITY> lsSubs;
	private List<COMMON_ENTITY> lsL3PGs;
	private List<COMMON_ENTITY> lsL4PGs;
	private List<COMMON_ENTITY> lsPRODUCTOFFERs;
	private List<COMMON_ENTITY> lsBFSs;
	private List<COMMON_ENTITY> lsCFSs;
	private List<COMMON_ENTITY> lsServices;
	private List<COMMON_ENTITY> lsRFSs;
	
	
	
	private static final String PRODUCTOFFER="PRODUCTOFFER";
	
	

	public PRODUCT_DATA() {
		// TODO Auto-generated constructor stub
	}



	/**
	 * @return the lsSubs
	 */
	public List<COMMON_ENTITY> getLsSubs() {
		return lsSubs;
	}



	/**
	 * @param lsSubs the lsSubs to set
	 */
	public void setLsSubs(List<COMMON_ENTITY> lsSubs) {
		this.lsSubs = lsSubs;
	}



	/**
	 * @return the lsL3PGs
	 */
	public List<COMMON_ENTITY> getLsL3PGs() {
		return lsL3PGs;
	}



	/**
	 * @param lsL3PGs the lsL3PGs to set
	 */
	public void setLsL3PGs(List<COMMON_ENTITY> lsL3PGs) {
		this.lsL3PGs = lsL3PGs;
	}



	/**
	 * @return the lsL4PGs
	 */
	public List<COMMON_ENTITY> getLsL4PGs() {
		return lsL4PGs;
	}



	/**
	 * @param lsL4PGs the lsL4PGs to set
	 */
	public void setLsL4PGs(List<COMMON_ENTITY> lsL4PGs) {
		this.lsL4PGs = lsL4PGs;
	}



	/**
	 * @return the lsPRODUCTOFFERs
	 */
	public List<COMMON_ENTITY> getLsPRODUCTOFFERs() {
		return lsPRODUCTOFFERs;
	}



	/**
	 * @param lsPRODUCTOFFERs the lsPRODUCTOFFERs to set
	 */
	public void setLsPRODUCTOFFERs(List<COMMON_ENTITY> lsPRODUCTOFFERs) {
		this.lsPRODUCTOFFERs = lsPRODUCTOFFERs;
	}



	/**
	 * @return the lsBFSs
	 */
	public List<COMMON_ENTITY> getLsBFSs() {
		return lsBFSs;
	}



	/**
	 * @param lsBFSs the lsBFSs to set
	 */
	public void setLsBFSs(List<COMMON_ENTITY> lsBFSs) {
		this.lsBFSs = lsBFSs;
	}



	/**
	 * @return the lsCFs
	 */
	public List<COMMON_ENTITY> getLsCFSs() {
		return lsCFSs;
	}



	/**
	 * @param lsCFs the lsCFs to set
	 */
	public void setLsCFSs(List<COMMON_ENTITY> lsCFSs) {
		this.lsCFSs = lsCFSs;
	}



	/**
	 * @return the lsServices
	 */
	public List<COMMON_ENTITY> getLsServices() {
		return lsServices;
	}



	/**
	 * @param lsServices the lsServices to set
	 */
	public void setLsServices(List<COMMON_ENTITY> lsServices) {
		this.lsServices = lsServices;
	}



	/**
	 * @return the lsRFSs
	 */
	public List<COMMON_ENTITY> getLsRFSs() {
		return lsRFSs;
	}



	/**
	 * @param lsRFSs the lsRFSs to set
	 */
	public void setLsRFSs(List<COMMON_ENTITY> lsRFSs) {
		this.lsRFSs = lsRFSs;
	}
	
	
	//for transformed data object
	public String toSQL() {
		StringBuilder sBuider = new StringBuilder();

		
		
		
		String sTemp = sBuider.toString();
		System.out.println(this.getClass().getName() + ".toSQL(): SQL statements are \n" + sTemp);
		
		sBuider = null;
		
		return sTemp;
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
