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
import com.fet.ice.simpleETL.extract.saxhandler.SAXHandler_CODETABLE;
import com.fet.ice.simpleETL.extract.saxhandler.SAXHandler_PRODUCTDATA;
import com.fet.ice.simpleETL.job.threadJob;
import com.fet.ice.simpleETL.load.SQLLoader_CODETABLE;
import com.fet.ice.simpleETL.load.SQLLoader_PRODUCT;
import com.fet.ice.simpleETL.load.SQLLoader_PROMOTION;
import com.fet.ice.simpleETL.transform.TransformedRecord;

public class ProductData_Extractor extends threadJob {

	private String xmlFileURI;
	private TransformedRecord transformedRec;
	private PRODUCT_DATA oProductData;

	// Due to xml may have large amount of data, use SAX parser instead of DOM,
	// XPATH, JAXB
	public ProductData_Extractor(String jobName, Logger logger, String xmlFileURI) {
		// TODO Auto-generated constructor stub
		super(jobName, logger);
		this.xmlFileURI = xmlFileURI;
		//logger.debug("ProductData_Extractor()");
		//logger.debug("xmlFileURI=" + xmlFileURI);
	}

	protected void doJob() {

		// execute Extract task
		// SAX parsing
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			// instantiate a SAXparser
			SAXParser saxParser = factory.newSAXParser();
			SAXHandler_PRODUCTDATA handler_PRODUCTDATA = new SAXHandler_PRODUCTDATA(logger);

			// parsing xml file
			saxParser.parse(xmlFileURI, handler_PRODUCTDATA);
			iStatus = this.JOB_EXTRACT_COMPLETED;

			//obtaining the parsed PROMOTIONDATA object list
			oProductData = handler_PRODUCTDATA.getPRODUCT_DATA();

			
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
		SQLLoader_PRODUCT loader = new SQLLoader_PRODUCT(logger);
		loader.perfomSync(oProductData);
		
		// return result
		iStatus = this.JOB_COMPLETED;
		
	}

}
