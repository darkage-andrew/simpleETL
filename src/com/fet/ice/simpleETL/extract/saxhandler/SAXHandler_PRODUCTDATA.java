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
import com.fet.ice.simpleETL.entity.COMMON_ENTITY;
import com.fet.ice.simpleETL.entity.PRODUCT_DATA;
import com.fet.ice.simpleETL.entity.PROMOTION_DATA;
import com.fet.ice.simpleETL.entity.COMMON_RELATIONITEM;
import com.fet.ice.simpleETL.entity.COMMON_ATTRIBUTE;

/**

 */
public class SAXHandler_PRODUCTDATA extends DefaultHandler {

	private static final int CURRENT_SUB = 1;
	private static final int CURRENT_L3PG = 2;
	private static final int CURRENT_L4PG = 3;
	private static final int CURRENT_PRODUCTOFFER = 4;
	private static final int CURRENT_BFS = 5;
	private static final int CURRENT_CFS = 6;
	private static final int CURRENT_SERVICE = 7;
	private static final int CURRENT_RFS = 8;
	private static final int CURRENT_RELATION_ITEM = 9;
	private static final int CURRENT_ATTRIBUTE = 10;

	private static int iCurrentObject;
	private static int iCurrentSubObject;

	private int iCount = 0;

	private String xmlFileName;
	private Logger logger;

	private HashMap<String, List<COMMON_ENTITY>> hmProducts;
	private PRODUCT_DATA oProductData;

	private COMMON_ENTITY oProductData_SUB;
	private List<COMMON_ENTITY> lsSUBs;

	private COMMON_ENTITY oProductData_L3PG;
	private List<COMMON_ENTITY> lsL3PGs;

	private COMMON_ENTITY oProductData_L4PG;
	private List<COMMON_ENTITY> lsL4PGs;

	private COMMON_ENTITY oProductData_PRODUCTOFFER;
	private List<COMMON_ENTITY> lsPRODUCTOFFERs;

	private COMMON_ENTITY oProductData_BFS;
	private List<COMMON_ENTITY> lsBFSs;

	private COMMON_ENTITY oProductData_CFS;
	private List<COMMON_ENTITY> lsCFSs;

	private COMMON_ENTITY oProductData_SERVICE;
	private List<COMMON_ENTITY> lsSERVICEs;

	private COMMON_ENTITY oProductData_RFS;
	private List<COMMON_ENTITY> lsRFSs;

	private List<COMMON_RELATIONITEM> lsRelationItems;
	private COMMON_RELATIONITEM oRelationItem;
	private List<COMMON_ATTRIBUTE> lsAttributes;
	private COMMON_ATTRIBUTE oAttribute;

	private String sObjVal;

	public SAXHandler_PRODUCTDATA(Logger logger) {
		super();
		this.logger = logger;
		iCurrentObject = 0;
		iCurrentSubObject = 0;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		sObjVal = "";

		if (qName.equalsIgnoreCase("PRODUCT_DATA")) {
			hmProducts = new HashMap<String, List<COMMON_ENTITY>>();
			logger.debug("PRODUCT_DATA");
		}

		// for parsing
		// SUBS============================================================
		if (qName.equalsIgnoreCase("SUBS")) {
			if (!hmProducts.containsKey("SUBS")) {
				// new COMMON_ENTITY with DATATYPE="SUB"
				lsSUBs = new ArrayList<COMMON_ENTITY>();
				hmProducts.put("SUBS", lsSUBs);
			}
			iCount = 0;
			logger.debug("--SUBS");
		}

		if (qName.equalsIgnoreCase("SUB")) {
			// keeps adding new record
			iCount += 1;
			oProductData_SUB = new COMMON_ENTITY("SUB");
			lsSUBs.add(oProductData_SUB);
			logger.debug("----SUB #" + iCount);

			this.iCurrentObject = this.CURRENT_SUB;
			this.iCurrentSubObject = 0;
		}

		// for parsing
		// L3PGS============================================================
		if (qName.equalsIgnoreCase("L3PGS")) {
			if (!hmProducts.containsKey("L3PGS")) {
				// new COMMON_ENTITY with DATATYPE="L3PG"
				this.lsL3PGs = new ArrayList<COMMON_ENTITY>();
				hmProducts.put("L3PGS", lsL3PGs);
			}
			logger.debug("--L3PGS");
			iCount = 0;
		}

		if (qName.equalsIgnoreCase("L3PG")) {
			// keeps adding new record
			iCount += 1;
			oProductData_L3PG = new COMMON_ENTITY("L3PG");
			lsL3PGs.add(oProductData_L3PG);
			logger.debug("----L3PG #" + iCount);

			this.iCurrentObject = this.CURRENT_L3PG;
			this.iCurrentSubObject = 0;
		}

		// for parsing
		// L4PGS============================================================
		if (qName.equalsIgnoreCase("L4PGS")) {
			if (!hmProducts.containsKey("L4PGS")) {
				// new COMMON_ENTITY with DATATYPE="L4PG"
				this.lsL4PGs = new ArrayList<COMMON_ENTITY>();
				hmProducts.put("L4PGS", lsL4PGs);
			}
			logger.debug("--L4PGS");
			iCount = 0;
		}

		if (qName.equalsIgnoreCase("L4PG")) {
			// keeps adding new record
			iCount += 1;
			oProductData_L4PG = new COMMON_ENTITY("L4PG");
			lsL4PGs.add(oProductData_L4PG);
			logger.debug("----L4PG #" + iCount);

			this.iCurrentObject = this.CURRENT_L4PG;
			this.iCurrentSubObject = 0;
		}

		// for parsing
		// PRODUCTOFFERS============================================================
		if (qName.equalsIgnoreCase("PRODUCTOFFERS")) {
			if (!hmProducts.containsKey("PRODUCTOFFERS")) {
				// new COMMON_ENTITY with DATATYPE="PRODUCTOFFER"
				this.lsPRODUCTOFFERs = new ArrayList<COMMON_ENTITY>();
				hmProducts.put("PRODUCTOFFERS", lsPRODUCTOFFERs);
			}
			logger.debug("--PRODUCTOFFERS");
			iCount = 0;
		}

		if (qName.equalsIgnoreCase("PRODUCTOFFER")) {
			// keeps adding new record
			iCount += 1;
			this.oProductData_PRODUCTOFFER = new COMMON_ENTITY("PRODUCTOFFER");
			lsPRODUCTOFFERs.add(oProductData_PRODUCTOFFER);
			logger.debug("----PRODUCTOFFER #"+iCount);

			this.iCurrentObject = this.CURRENT_PRODUCTOFFER;
			this.iCurrentSubObject = 0;
		}

		// for parsing
		// BFSS============================================================
		if (qName.equalsIgnoreCase("BFSS")) {
			if (!hmProducts.containsKey("BFSS")) {
				// new COMMON_ENTITY with DATATYPE="BFS"
				this.lsBFSs = new ArrayList<COMMON_ENTITY>();
				hmProducts.put("BFSS", lsBFSs);
			}
			logger.debug("--BFSS");
			iCount = 0;
		}

		if (qName.equalsIgnoreCase("BFS")) {
			// keeps adding new record
			iCount += 1;
			this.oProductData_BFS = new COMMON_ENTITY("BFS");
			lsBFSs.add(oProductData_BFS);
			logger.debug("----BFS #"+iCount);

			this.iCurrentObject = this.CURRENT_BFS;
			this.iCurrentSubObject = 0;
		}

		// for parsing
		// CFSS============================================================
		if (qName.equalsIgnoreCase("CFSS")) {
			if (!hmProducts.containsKey("CFSS")) {
				// new COMMON_ENTITY with DATATYPE="CFS"
				this.lsCFSs = new ArrayList<COMMON_ENTITY>();
				hmProducts.put("CFSS", lsCFSs);
			}
			logger.debug("--CFSS");
			iCount = 0;

		}

		if (qName.equalsIgnoreCase("CFS")) {
			// keeps adding new record
			iCount += 1;
			this.oProductData_CFS = new COMMON_ENTITY("CFS");
			lsCFSs.add(oProductData_CFS);
			logger.debug("----CFS #"+iCount);

			this.iCurrentObject = this.CURRENT_CFS;
			this.iCurrentSubObject = 0;

		}

		// for parsing
		// SERVICES============================================================
		if (qName.equalsIgnoreCase("SERVICES")) {
			if (!hmProducts.containsKey("SERVICES")) {
				// new COMMON_ENTITY with DATATYPE="SERVICE"
				this.lsSERVICEs = new ArrayList<COMMON_ENTITY>();
				hmProducts.put("SERVICES", lsSERVICEs);
			}
			logger.debug("--SERVICES");
			iCount = 0;
		}

		if (qName.equalsIgnoreCase("SERVICE")) {
			iCount += 1;
			// keeps adding new record
			this.oProductData_SERVICE = new COMMON_ENTITY("SERVICE");
			lsSERVICEs.add(oProductData_SERVICE);
			logger.debug("----SERVICE #"+iCount);

			this.iCurrentObject = this.CURRENT_SERVICE;
			this.iCurrentSubObject = 0;
		}

		// for parsing
		// RFSS============================================================
		if (qName.equalsIgnoreCase("RFSS")) {
			if (!hmProducts.containsKey("RFSS")) {
				// new COMMON_ENTITY with DATATYPE="RFS"
				this.lsRFSs = new ArrayList<COMMON_ENTITY>();
				hmProducts.put("RFSS", lsRFSs);
			}
			logger.debug("--RFSS");
			iCount = 0;
		}

		if (qName.equalsIgnoreCase("RFS")) {
			iCount += 1;
			// keeps adding new record
			this.oProductData_RFS = new COMMON_ENTITY("RFS");
			lsRFSs.add(oProductData_RFS);
			logger.debug("----RFS #"+iCount );

			this.iCurrentObject = this.CURRENT_RFS;
			this.iCurrentSubObject = 0;
		}

		// children data - relation_items, got to do multiple check based on
		// iCurrentObject
		if (qName.equalsIgnoreCase("ITEMRELATIONS")) {
			// construct COMMON_RELATIONITEM list
			lsRelationItems = new ArrayList<COMMON_RELATIONITEM>();

			switch (iCurrentObject) {
			case 1:
				oProductData_SUB.setLsItemRelations(lsRelationItems);
				break;
			case 2:
				this.oProductData_L3PG.setLsItemRelations(lsRelationItems);
				break;
			case 3:
				this.oProductData_L4PG.setLsItemRelations(lsRelationItems);
				break;
			case 4:
				this.oProductData_PRODUCTOFFER.setLsItemRelations(lsRelationItems);
				break;
			case 5:
				this.oProductData_BFS.setLsItemRelations(lsRelationItems);
				break;
			case 6:
				this.oProductData_CFS.setLsItemRelations(lsRelationItems);
				break;
			case 7:
				this.oProductData_SERVICE.setLsItemRelations(lsRelationItems);
				break;
			case 8:
				this.oProductData_RFS.setLsItemRelations(lsRelationItems);
				break;
			}

			iCurrentSubObject = this.CURRENT_RELATION_ITEM;
			logger.debug("------ITEMRELATIONS");
		}

		if (qName.equalsIgnoreCase("RELATION_ITEM")) {
			// construct COMMON_RELATIONITEM object
			oRelationItem = new COMMON_RELATIONITEM();
			lsRelationItems.add(oRelationItem);

			iCurrentSubObject = this.CURRENT_RELATION_ITEM;
			logger.debug("--------ITEMRELATION");
		}

		// children data - attributes, got to do multiple check based on
		// iCurrentObject
		if (qName.equalsIgnoreCase("ATTRIBUTES")) {

			// construct ATTRIBUTE list
			lsAttributes = new ArrayList<COMMON_ATTRIBUTE>();

			switch (this.iCurrentObject) {
			case 1:
				oProductData_SUB.setLsAttributes(lsAttributes);
				break;
			case 2:
				this.oProductData_L3PG.setLsAttributes(lsAttributes);
				break;
			case 3:
				this.oProductData_L4PG.setLsAttributes(lsAttributes);
				break;
			case 4:
				this.oProductData_PRODUCTOFFER.setLsAttributes(lsAttributes);
				break;
			case 5:
				this.oProductData_BFS.setLsAttributes(lsAttributes);
				break;
			case 6:
				this.oProductData_CFS.setLsAttributes(lsAttributes);
				break;
			case 7:
				this.oProductData_SERVICE.setLsAttributes(lsAttributes);
				break;
			case 8:
				this.oProductData_RFS.setLsAttributes(lsAttributes);
				break;
			}

			iCurrentSubObject = this.CURRENT_ATTRIBUTE;
			logger.debug("------ATTRIBUTES");
		}

		if (qName.equalsIgnoreCase("ATTRIBUTE")) {
			// construct COMMON_ATTRIBUTE object
			oAttribute = new COMMON_ATTRIBUTE();
			lsAttributes.add(oAttribute);

			iCurrentSubObject = this.CURRENT_ATTRIBUTE;
			logger.debug("--------ATTRIBUTE");
		}

	}

	public void endElement(String uri, String localName, String qName) throws SAXException {

		// for parsing
		// SUBS============================================================
		if (this.iCurrentObject == this.CURRENT_SUB) {
			endElement_ProductData(qName, oProductData_SUB);
		}
		// for parsing
		// SUBS============================================================

		// for parsing
		// L3PGS============================================================
		if (this.iCurrentObject == this.CURRENT_L3PG) {
			endElement_ProductData(qName, oProductData_L3PG);
		}
		// for parsing
		// L3PGS============================================================

		// for parsing
		// L4PGS============================================================
		if (this.iCurrentObject == this.CURRENT_L4PG) {
			endElement_ProductData(qName, oProductData_L4PG);
		}
		// for parsing
		// L4PGS===========================================================

		// for parsing
		// PRODUCTOFFERS=================================================================
		if (this.iCurrentObject == this.CURRENT_PRODUCTOFFER) {
			endElement_ProductData(qName, oProductData_PRODUCTOFFER);
		}
		// for parsing
		// PRODUCTOFFERS=================================================================

		// for parsing
		// BFSS============================================================
		if (this.iCurrentObject == this.CURRENT_BFS) {
			endElement_ProductData(qName, oProductData_BFS);
		}
		// for parsing
		// BFSS===================================================================

		// for parsing
		// CFSS============================================================
		if (this.iCurrentObject == this.CURRENT_CFS) {
			endElement_ProductData(qName, oProductData_CFS);
		}
		// for parsing
		// CFSS===================================================================

		// for parsing
		// SERVICES============================================================
		if (this.iCurrentObject == this.CURRENT_SERVICE) {
			endElement_ProductData(qName, oProductData_SERVICE);
		}
		// for parsing
		// SERVICES===================================================================

		// for parsing
		// RFSS============================================================
		if (this.iCurrentObject == this.CURRENT_RFS) {
			endElement_ProductData(qName, oProductData_RFS);
		}
		// for parsing
		// RFSS===================================================================

	}

	// for further fine tune purpose
	private void endElement_ProductData(String qName, COMMON_ENTITY oProductData) {
		
		if (qName.equalsIgnoreCase("ITEMCODE")) {
			// construct SUB object with attributes
			oProductData.setITEMCODE(sObjVal);
			logger.debug("------ITEMCODE=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("ITEMTYPE")) {
			// construct SUB object with attributes
			oProductData.setITEMTYPE(sObjVal);
			logger.debug("------ITEMTYPE=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("NAME") && (iCurrentSubObject == 0)) {
			// construct SUB object with attributes
			oProductData.setNAME(sObjVal);
			logger.debug("------NAME=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("STARTDATE") && (iCurrentSubObject == 0)) {
			// construct SUB object with attributes
			oProductData.setSTARTDATE(sObjVal);
			logger.debug("------STARTDATE=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("ENDDATE") && (iCurrentSubObject == 0)) {
			// construct SUB object with attributes
			oProductData.setENDDATE(sObjVal);
			logger.debug("------ENDDATE=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("ORDERABLE")) {
			// construct SUB object with attributes
			oProductData.setORDERABLE(sObjVal);
			logger.debug("------ORDERABLE=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("STATUS") && (iCurrentSubObject == 0)) {
			// construct SUB object with attributes
			oProductData.setSTATUS(sObjVal);
			logger.debug("------STATUS=" + sObjVal);
		}

		// children records - RelationItems ====================================

		// set COMMON_RELATIONITEM attributes
		if (qName.equalsIgnoreCase("ITEMRELATIONTARGET")) {
			oRelationItem.setITEMRELATIONTARGET(sObjVal);
			logger.debug("----------ITEMRELATIONTARGET=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("ITEMRELATIONCODE")) {
			oRelationItem.setITEMRELATIONCODE(sObjVal);
			logger.debug("----------ITEMRELATIONCODE=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("STATUS") && (iCurrentSubObject == CURRENT_RELATION_ITEM)) {
			oRelationItem.setSTATUS(sObjVal);
			logger.debug("----------STATUS=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("STARTDATE") && (iCurrentSubObject == CURRENT_RELATION_ITEM)) {
			oRelationItem.setSTARTDATE(sObjVal);
			logger.debug("----------STARTDATE=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("ENDDATE") && (iCurrentSubObject == CURRENT_RELATION_ITEM)) {
			oRelationItem.setENDDATE(sObjVal);
			logger.debug("----------ENDDATE=" + sObjVal);
		}

		// children records - Attributes
		// ========================================

		// set COMMON_ATTRIBUTE attribute
		if (qName.equalsIgnoreCase("ATTRIBUTECODE")) {
			oAttribute.setATTRIBUTECODE(sObjVal);
			logger.debug("----------ATTRIBUTECODE=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("ASSOCIATIONTYPE")) {
			oAttribute.setASSOCIATIONTYPE(sObjVal);
			logger.debug("----------ASSOCIATIONTYPE=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("ITEMRELATIONCODE") && (iCurrentSubObject == CURRENT_ATTRIBUTE)) {
			oAttribute.setITEMRELATIONCODE(sObjVal);
			logger.debug("----------ITEMRELATIONCODE=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("TYPE")) {
			oAttribute.setTYPE(sObjVal);
			logger.debug("----------TYPE=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("STATUS") && (iCurrentSubObject == CURRENT_ATTRIBUTE)) {
			oAttribute.setSTATUS(sObjVal);
			logger.debug("----------STATUS=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("NAME") && (iCurrentSubObject == CURRENT_ATTRIBUTE)) {
			oAttribute.setNAME(sObjVal);
			logger.debug("----------NAME=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("ITEMATTRIBUTECODE")) {
			oAttribute.setITEMATTRIBUTECODE(sObjVal);
			logger.debug("----------ITEMATTRIBUTECODE=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("STARTDATE") && (iCurrentSubObject == CURRENT_ATTRIBUTE)) {
			oAttribute.setSTARTDATE(sObjVal);
			logger.debug("----------STARTDATE=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("ENDDATE") && (iCurrentSubObject == CURRENT_ATTRIBUTE)) {
			oAttribute.setENDDATE(sObjVal);
			logger.debug("----------ENDDATE=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("DEFAULTVALUE")) {
			oAttribute.setDEFAULTVALUE(sObjVal);
			logger.debug("----------DEFAULTVALUE=" + sObjVal);
		}
	}

	public void characters(char ch[], int start, int length) throws SAXException {
		sObjVal = new String(ch, start, length).trim();
	}

	/**
	 * @return the hmProducts
	 */
	public HashMap getHmProducts() {
		return hmProducts;
	}

	/**
	 * @param hmProducts
	 *            the hmProducts to set
	 */
	public void setHmProducts(HashMap hmProducts) {
		this.hmProducts = hmProducts;
	}

	/**
	 * @return PRODUCT_DATA
	 */
	public PRODUCT_DATA getPRODUCT_DATA() {
		oProductData = new PRODUCT_DATA();
		
		oProductData.setLsSubs((List<COMMON_ENTITY>) this.hmProducts.get("SUBS"));
		oProductData.setLsSubs((List<COMMON_ENTITY>) this.hmProducts.get("L3PGS"));
		oProductData.setLsSubs((List<COMMON_ENTITY>) this.hmProducts.get("L4PGS"));
		oProductData.setLsSubs((List<COMMON_ENTITY>) this.hmProducts.get("PRODUCTOFFERS"));
		oProductData.setLsSubs((List<COMMON_ENTITY>) this.hmProducts.get("BFSS"));
		oProductData.setLsSubs((List<COMMON_ENTITY>) this.hmProducts.get("CFSS"));
		oProductData.setLsSubs((List<COMMON_ENTITY>) this.hmProducts.get("SERVICES"));
		oProductData.setLsSubs((List<COMMON_ENTITY>) this.hmProducts.get("RFSS"));

		return oProductData;
	}

}
