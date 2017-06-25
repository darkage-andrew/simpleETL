package com.fet.ice.simpleETL.exception;

public class jobException extends Exception {

	private int errCode;
	private String errMsg;
	
	public jobException()
	{
		super();
	}
	
	public jobException(String msg)
	{
		super();
		this.errMsg = msg;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	
}
