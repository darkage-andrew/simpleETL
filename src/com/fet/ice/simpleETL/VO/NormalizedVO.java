package com.fet.ice.simpleETL.VO;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class NormalizedVO {

	private String rec_Val;

	public NormalizedVO() {

	}

	public NormalizedVO(String sVal) {
		this.rec_Val = sVal;
	}

	/**
	 * @return the rec_Val
	 */
	public String getRec_Val() {
		return rec_Val;
	}

	/**
	 * @param rec_Val the rec_Val to set
	 */
	public void setRec_Val(String rec_Val) {
		this.rec_Val = rec_Val;
	}




}
