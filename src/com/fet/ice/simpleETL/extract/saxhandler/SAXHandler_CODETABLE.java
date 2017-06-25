package com.fet.ice.simpleETL.extract.saxhandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.fet.ice.simpleETL.entity.CODETABLE_VALUEITEM;
import com.fet.ice.simpleETL.entity.CODE_TABLE;

/**

 */
public class SAXHandler_CODETABLE extends DefaultHandler {

	private static final int CURRENT_CODETABLE=1;
	private static final int CURRENT_VALUE_ITEM=2;
	
	private static int iCurrentObject;
	
	
	private String xmlFileName;
	private Logger logger;

	private List<CODE_TABLE> lsCODETABLE;
	private CODE_TABLE oCodeTable;
	private List lsVALUEITEMS;
	private CODETABLE_VALUEITEM oValueItem;
	private String sObjVal;
	
	private int iCount = 0;

	
	public SAXHandler_CODETABLE(Logger logger) {
		super();
		this.logger = logger;
	}


	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// for CODE_TABLE, nothing to do for startElement
		sObjVal = "";
		
		//logger.debug("CODE_TABLE.startElement(): qname=" + qName + ", sObjVal is " + sObjVal );
		
		
		if (qName.equalsIgnoreCase("CODETABLES")) {
			if (lsCODETABLE == null) {
				lsCODETABLE = new ArrayList<CODE_TABLE>();
			}
			logger.debug("CODETABLES");
		}

		
		if (qName.equalsIgnoreCase("CODETABLE")) {
			iCount += 1;
			oCodeTable = new CODE_TABLE();
			lsCODETABLE.add(oCodeTable);
			logger.debug("-- CODETABLE#" + iCount);
			iCurrentObject = this.CURRENT_CODETABLE;
		}
		
		if (qName.equalsIgnoreCase("VALUE_ITEMS")) {
			if (oCodeTable.getLsVALUEITEMS() == null) {
				lsVALUEITEMS = new ArrayList<CODETABLE_VALUEITEM>();
				oCodeTable.setLsVALUEITEMS(lsVALUEITEMS);
			}
			logger.debug("---- VALUE_ITEMS");
			iCurrentObject = this.CURRENT_VALUE_ITEM;
		}
		
		if (qName.equalsIgnoreCase("VALUE_ITEM")) {
			oValueItem = new CODETABLE_VALUEITEM();
			lsVALUEITEMS.add(oValueItem);
			logger.debug("------ VALUE_ITEM");
			iCurrentObject = this.CURRENT_VALUE_ITEM;
		}
		
		
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {

		//logger.debug("CODE_TABLE.endElement(): qname=" + qName + ", sObjVal is " + sObjVal );
		

		if (qName.equalsIgnoreCase("ATTRIBUTECODE")) {
			oCodeTable.setATTRIBUTECODE(sObjVal);
			logger.debug("---- ATTRIBUTECODE=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("CODETABLENAME")) {
			oCodeTable.setCODETABLENAME(sObjVal);
			logger.debug("---- CODETABLENAME=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("NAME")) {
			oCodeTable.setNAME(sObjVal);
			logger.debug("---- NAME=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("DESCRIPTION") && iCurrentObject == 1) {
			oCodeTable.setDESCRIPTION(sObjVal);
			logger.debug("---- DESCRIPTION=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("STARTDATE")) {
			oCodeTable.setSTARTDATE(sObjVal);
			logger.debug("---- STARTDATE=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("ENDDATE")) {
			oCodeTable.setENDDATE(sObjVal);
			logger.debug("---- ENDDATE=" + sObjVal);
		}
		
		
		if (qName.equalsIgnoreCase("CODE") && iCurrentObject == 2) {
			oValueItem.setCODE(sObjVal);
			logger.debug("-------- CODE="+sObjVal);
		}

		if (qName.equalsIgnoreCase("DESCRIPTION") && iCurrentObject == 2) {
			oValueItem.setDESCRIPTION(sObjVal);
			logger.debug("-------- DESCRIPTION="+sObjVal);
		}

	}

	public void characters(char ch[], int start, int length) throws SAXException {
		sObjVal = new String(ch, start, length).trim();
		//logger.debug("CODE_TABLE.characters(), sObjVal=" + sObjVal );
	}

	/**
	 * @return the lsCODETABLE
	 */
	public List<CODE_TABLE> getLsCODETABLE() {
		return lsCODETABLE;
	}

	/**
	 * @param lsCODETABLE
	 *            the lsCODETABLE to set
	 */
	public void setLsCODETABLE(List<CODE_TABLE> lsCODETABLE) {
		this.lsCODETABLE = lsCODETABLE;
	}

}
