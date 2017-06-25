package com.fet.ice.simpleETL.entity;

import java.util.List;

import com.fet.ice.simpleETL.transform.TransformedRecord;

public class PROMOTION_DATA {

	private List<COMMON_ENTITY> lsGROUPs;
	private List<COMMON_ENTITY> lsACTIVITIES;
	private List<COMMON_ENTITY> lsPROMOTIONs;
	private List<COMMON_ENTITY> lsDEVICEs;
	
	
		
	public PROMOTION_DATA() {
		// TODO Auto-generated constructor stub
	}



	/**
	 * @return the lsGROUPs
	 */
	public List<COMMON_ENTITY> getLsGROUPs() {
		return lsGROUPs;
	}



	/**
	 * @param lsGROUPs the lsGROUPs to set
	 */
	public void setLsGROUPs(List<COMMON_ENTITY> lsGROUPs) {
		this.lsGROUPs = lsGROUPs;
	}



	/**
	 * @return the lsACTIVITIES
	 */
	public List<COMMON_ENTITY> getLsACTIVITIES() {
		return lsACTIVITIES;
	}



	/**
	 * @param lsACTIVITIES the lsACTIVITIES to set
	 */
	public void setLsACTIVITIES(List<COMMON_ENTITY> lsACTIVITIES) {
		this.lsACTIVITIES = lsACTIVITIES;
	}



	/**
	 * @return the lsPROMOTIONs
	 */
	public List<COMMON_ENTITY> getLsPROMOTIONs() {
		return lsPROMOTIONs;
	}



	/**
	 * @param lsPROMOTIONs the lsPROMOTIONs to set
	 */
	public void setLsPROMOTIONs(List<COMMON_ENTITY> lsPROMOTIONs) {
		this.lsPROMOTIONs = lsPROMOTIONs;
	}



	/**
	 * @return the lsDEVICEs
	 */
	public List<COMMON_ENTITY> getLsDEVICEs() {
		return lsDEVICEs;
	}



	/**
	 * @param lsDEVICEs the lsDEVICEs to set
	 */
	public void setLsDEVICEs(List<COMMON_ENTITY> lsDEVICEs) {
		this.lsDEVICEs = lsDEVICEs;
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
