package com.fet.ice.simpleETL.entity;

public class CODETABLE_VALUEITEM {

	private String CODE;
	private String DESCRIPTION;
	


	public CODETABLE_VALUEITEM() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public CODETABLE_VALUEITEM(String cODE, String dESCRIPTION) {
		super();
		CODE = cODE;
		DESCRIPTION = dESCRIPTION;
	}


	/**
	 * @return the cODE
	 */
	public String getCODE() {
		return CODE;
	}



	/**
	 * @param cODE the cODE to set
	 */
	public void setCODE(String cODE) {
		CODE = cODE;
	}



	/**
	 * @return the dESCRIPTION
	 */
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}



	/**
	 * @param dESCRIPTION the dESCRIPTION to set
	 */
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}


}
