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
public class SAXHandler_PROMOTIONDATA extends DefaultHandler {

	private static final int CURRENT_PROMOTIONGROUP = 1;
	private static final int CURRENT_ACTIVITY = 2;
	private static final int CURRENT_PROMOTION = 3;
	private static final int CURRENT_DEVICE = 4;
	private static final int CURRENT_RELATION_ITEM = 5;
	private static final int CURRENT_ATTRIBUTE = 6;

	private String xmlFileName;
	private Logger logger;

	private HashMap<String, List<COMMON_ENTITY>> hmPromotionData;
	private PROMOTION_DATA oPromotionData;

	private COMMON_ENTITY oPromotion_GROUP;
	private List<COMMON_ENTITY> lsGROUPs;

	private COMMON_ENTITY oPromotion_ACTITY;
	private List<COMMON_ENTITY> lsACTIVITIES;

	private COMMON_ENTITY oPromotion_PROMOTION;
	private List<COMMON_ENTITY> lsPROMOTIONs;

	private COMMON_ENTITY oPromotion_DEVICE;
	private List<COMMON_ENTITY> lsDEVICEs;

	private List<COMMON_RELATIONITEM> lsRelationItems;
	private COMMON_RELATIONITEM oRelationItem;
	private List<COMMON_ATTRIBUTE> lsAttributes;
	private COMMON_ATTRIBUTE oAttribute;

	private static int iCurrentObject;
	private static int iCurrentSubObject;
	private int iCount = 0;

	private String sObjVal;

	public SAXHandler_PROMOTIONDATA(Logger logger) {
		super();
		this.logger = logger;
		iCurrentObject = 0;
		iCurrentSubObject = 0;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		sObjVal = "";

		if (qName.equalsIgnoreCase("PROMOTION_DATA")) {
			hmPromotionData = new HashMap<String, List<COMMON_ENTITY>>();
			logger.debug("PROMOTION_DATA");
		}

		// Parsing PROMOTION_GROUPS
		// ===============================================
		if (qName.equalsIgnoreCase("PROMOTION_GROUPS")) {
			if (!hmPromotionData.containsKey("PROMOTION_GROUPS")) {
				this.lsGROUPs = new ArrayList<COMMON_ENTITY>();
				hmPromotionData.put("PROMOTION_GROUPS", lsGROUPs);
			}
			logger.debug("--PROMOTION_GROUPS");
			iCount = 0;
		}

		if (qName.equalsIgnoreCase("PROMOTION_GROUP")) {
			iCount += 1;
			// keeps adding new record
			// new COMMON_ENTITY with DATATYPE="PROMOTION_GROUP"
			this.oPromotion_GROUP = new COMMON_ENTITY("PROMOTION_GROUP");
			lsGROUPs.add(oPromotion_GROUP);

			logger.debug("----PROMOTION_GROUP #" + iCount);

			this.iCurrentObject = this.CURRENT_PROMOTIONGROUP;
			this.iCurrentSubObject = 0;
		}

		// for parsing ACTIVITIES ===================================
		if (qName.equalsIgnoreCase("ACTIVITIES")) {
			if (!hmPromotionData.containsKey("ACTIVITIES")) {
				this.lsACTIVITIES = new ArrayList<COMMON_ENTITY>();
				hmPromotionData.put("ACTIVITIES", lsACTIVITIES);
			}
			logger.debug("--ACTIVITIES");
			iCount = 0;
		}

		if (qName.equalsIgnoreCase("ACTIVITY")) {
			iCount += 1;
			// keeps adding new record
			// new COMMON_ENTITY with DATATYPE="ACTIVITY"
			this.oPromotion_ACTITY = new COMMON_ENTITY("ACTIVITY");
			lsACTIVITIES.add(oPromotion_ACTITY);

			logger.debug("----ACTIVITY #" + iCount);

			this.iCurrentObject = this.CURRENT_ACTIVITY;
			this.iCurrentSubObject = 0;
		}

		// for parsing PROMOTIONS ===================================
		if (qName.equalsIgnoreCase("PROMOTIONS")) {
			if (!hmPromotionData.containsKey("PROMOTIONS")) {
				this.lsPROMOTIONs = new ArrayList<COMMON_ENTITY>();
				hmPromotionData.put("PROMOTIONS", lsPROMOTIONs);
			}
			logger.debug("--PROMOTIONS");
			iCount = 0;
		}

		if (qName.equalsIgnoreCase("PROMOTION")) {
			iCount += 1;
			// keeps adding new record
			// new COMMON_ENTITY with DATATYPE="PROMOTION"
			this.oPromotion_PROMOTION = new COMMON_ENTITY("PROMOTION");
			lsPROMOTIONs.add(oPromotion_PROMOTION);

			logger.debug("----PROMOTION #" + iCount);

			this.iCurrentObject = this.CURRENT_PROMOTION;
			this.iCurrentSubObject = 0;
		}

		// for parsing DEVICES ===================================
		if (qName.equalsIgnoreCase("DEVICES")) {
			if (!hmPromotionData.containsKey("DEVICES")) {
				this.lsDEVICEs = new ArrayList<COMMON_ENTITY>();
				hmPromotionData.put("DEVICES", lsDEVICEs);
			}
			logger.debug("--DEVICES");
			iCount = 0;
		}

		if (qName.equalsIgnoreCase("DEVICE")) {
			iCount += 1;
			// keeps adding new record
			// new COMMON_ENTITY with DATATYPE="DEVICE"
			this.oPromotion_DEVICE = new COMMON_ENTITY("DEVICE");
			lsDEVICEs.add(oPromotion_DEVICE);
			logger.debug("----DEVICE #" + iCount);

			this.iCurrentObject = this.CURRENT_DEVICE;
			this.iCurrentSubObject = 0;
		}

		// children data - relation_items, got to do multiple check based on
		// iCurrentObject
		if (qName.equalsIgnoreCase("ITEMRELATIONS")) {
			// construct COMMON_RELATIONITEM list
			lsRelationItems = new ArrayList<COMMON_RELATIONITEM>();

			switch (iCurrentObject) {
			case 1:
				this.oPromotion_GROUP.setLsItemRelations(lsRelationItems);
				break;
			case 2:
				this.oPromotion_ACTITY.setLsItemRelations(lsRelationItems);
				break;
			case 3:
				this.oPromotion_PROMOTION.setLsItemRelations(lsRelationItems);
				break;
			case 4:
				this.oPromotion_DEVICE.setLsItemRelations(lsRelationItems);
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
				this.oPromotion_GROUP.setLsAttributes(lsAttributes);
				break;
			case 2:
				this.oPromotion_ACTITY.setLsAttributes(lsAttributes);
				break;
			case 3:
				this.oPromotion_PROMOTION.setLsAttributes(lsAttributes);
				break;
			case 4:
				this.oPromotion_DEVICE.setLsAttributes(lsAttributes);
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

		// parsing PROMOTION_GROUP =============
		if (this.iCurrentObject == this.CURRENT_PROMOTIONGROUP) {
			endElement_PromotionData(qName, oPromotion_GROUP);
		}

		// parsing ACTIVITY =============
		if (this.iCurrentObject == this.CURRENT_ACTIVITY) {
			endElement_PromotionData(qName, oPromotion_ACTITY);
		}

		// parsing PROMOTION =============
		if (this.iCurrentObject == this.CURRENT_PROMOTION) {
			endElement_PromotionData(qName, oPromotion_PROMOTION);
		}

		// parsing DEVICE =============
		if (this.iCurrentObject == this.CURRENT_DEVICE) {
			endElement_PromotionData(qName, oPromotion_DEVICE);
		}

	}

	// for further fine tune purpose
	private void endElement_PromotionData(String qName, COMMON_ENTITY oEntity) {
		
		if (qName.equalsIgnoreCase("ITEMCODE")) {
			// construct SUB object with attributes
			oEntity.setITEMCODE(sObjVal);
			logger.debug("------ITEMCODE=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("ITEMTYPE")) {
			// construct SUB object with attributes
			oEntity.setITEMTYPE(sObjVal);
			logger.debug("------ITEMTYPE=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("NAME") && (iCurrentSubObject == 0)) {
			// construct SUB object with attributes
			oEntity.setNAME(sObjVal);
			logger.debug("------NAME=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("STARTDATE") && (iCurrentSubObject == 0)) {
			// construct SUB object with attributes
			oEntity.setSTARTDATE(sObjVal);
			logger.debug("------STARTDATE=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("ENDDATE") && (iCurrentSubObject == 0)) {
			// construct SUB object with attributes
			oEntity.setENDDATE(sObjVal);
			logger.debug("------ENDDATE=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("ORDERABLE")) {
			// construct SUB object with attributes
			oEntity.setORDERABLE(sObjVal);
			logger.debug("------ORDERABLE=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("STATUS") && (iCurrentSubObject == 0)) {
			// construct SUB object with attributes
			oEntity.setSTATUS(sObjVal);
			logger.debug("------STATUS=" + sObjVal);
		}

		// children records - RelationItems ====================================

		// set COMMON_RELATIONITEM attributes
		if (qName.equalsIgnoreCase("ITEMRELATIONTARGET")) {
			oRelationItem.setITEMRELATIONTARGET(sObjVal);
			logger.debug("----------ITEMRELATIONTARGET=" + sObjVal);
		}

		if (qName.equalsIgnoreCase("ITEMRELATIONCODE") && (iCurrentSubObject == CURRENT_RELATION_ITEM)) {
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
	 * @return PRODUCT_DATA
	 */
	public PROMOTION_DATA getPROMOTION_DATA() {
		oPromotionData = new PROMOTION_DATA();
		this.oPromotionData.setLsGROUPs((List<COMMON_ENTITY>) this.hmPromotionData.get("PROMOTION_GROUPS"));
		this.oPromotionData.setLsACTIVITIES((List<COMMON_ENTITY>) this.hmPromotionData.get("ACTIVITIES"));
		this.oPromotionData.setLsPROMOTIONs((List<COMMON_ENTITY>) this.hmPromotionData.get("PROMOTIONS"));
		this.oPromotionData.setLsDEVICEs((List<COMMON_ENTITY>) this.hmPromotionData.get("DEVICES"));

		return oPromotionData;
	}

}
