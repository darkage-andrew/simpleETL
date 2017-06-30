package com.fet.ice.simpleETL.extract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import com.fet.ice.simpleETL.entity.CODE_TABLE;
import com.fet.ice.simpleETL.entity.COMMON_ENTITY;
import com.fet.ice.simpleETL.entity.PRODUCT_DATA;
import com.fet.ice.simpleETL.entity.PROMOTION_DATA;
import com.fet.ice.simpleETL.extract.saxhandler.SAXHandler_CODETABLE;
import com.fet.ice.simpleETL.extract.saxhandler.SAXHandler_PRODUCTDATA;
import com.fet.ice.simpleETL.extract.saxhandler.SAXHandler_PROMOTIONDATA;
import com.fet.ice.simpleETL.job.threadJob;
import com.fet.ice.simpleETL.load.SQLLoader_CODETABLE;
import com.fet.ice.simpleETL.load.SQLLoader_PRODUCT;
import com.fet.ice.simpleETL.load.SQLLoader_PROMOTION;
import com.fet.ice.simpleETL.transform.TransformedRecord;

public class PromotionData_Extractor extends threadJob {

	private String xmlFileURI;
	private TransformedRecord transformedRec;
	private PROMOTION_DATA oPromotionData;


	// Due to xml may have large amount of data, use SAX parser instead of DOM,
	// XPATH, JAXB
	public PromotionData_Extractor(String jobName, Logger logger, String xmlFileURI) {
		// TODO Auto-generated constructor stub
		super(jobName, logger);
		this.xmlFileURI = xmlFileURI;
		//logger.debug("PromotionData_Extractor()");
		//logger.debug("xmlFileURI=" + xmlFileURI);
	}

	protected void doJob() {
		
		// execute Extract task
		// SAX parsing
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			// instantiate a SAXparser
			SAXParser saxParser = factory.newSAXParser();
			SAXHandler_PROMOTIONDATA handler_PROMOTIONDATA = new SAXHandler_PROMOTIONDATA(logger);

			// parsing xml file
			saxParser.parse(xmlFileURI, handler_PROMOTIONDATA);
			iStatus = this.JOB_EXTRACT_COMPLETED;
			
			//obtaining the parsed PROMOTIONDATA object list
			oPromotionData = handler_PROMOTIONDATA.getPROMOTION_DATA();


		} catch (ParserConfigurationException pce) {
			logger.debug(pce.getMessage());
			iStatus = this.JOB_ERROR;
		} catch (SAXException se) {
			logger.debug(se.getMessage());
			iStatus = this.JOB_ERROR;
		} catch (IOException ioe) {
			logger.debug(ioe.getMessage());
			iStatus = this.JOB_ERROR;
		}

		// execute Load tasks
		SQLLoader_PROMOTION loader = new SQLLoader_PROMOTION(logger);
		loader.perfomSync(oPromotionData);
		
		// return result
		iStatus = this.JOB_COMPLETED;
		
	}

}
