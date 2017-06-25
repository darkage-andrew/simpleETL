package com.fet.ice.simpleETL.extract;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.fet.ice.VO.NormalizedVO;
import com.fet.ice.simpleETL.entity.CODE_TABLE;
import com.fet.ice.simpleETL.extract.saxhandler.SAXHandler_CODETABLE;
import com.fet.ice.simpleETL.job.threadJob;
import com.fet.ice.simpleETL.load.SQLLoader_CODETABLE;
import com.fet.ice.simpleETL.transform.TransformedRecord;

import java.util.*;

public class CODETABLE_Extractor extends threadJob {

	private String xmlFileURI;
	private TransformedRecord transformedRec;
	private List<CODE_TABLE> lsCODETABLE;

	// Due to xml may have large amount of data, use SAX parser instead of DOM,
	// XPATH, JAXB
	public CODETABLE_Extractor(String jobName, Logger logger, String xmlFileURI) {
		super(jobName, logger);
		this.xmlFileURI = xmlFileURI;
	}

	protected void doJob() {
		
		// execute Extract task
		// SAX parsing
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			// instantiate a SAXparser
			SAXParser saxParser = factory.newSAXParser();
			SAXHandler_CODETABLE handler_CODETABLE = new SAXHandler_CODETABLE(logger);

			// parsing xml file
			saxParser.parse(xmlFileURI, handler_CODETABLE);
			iStatus = this.JOB_EXTRACT_COMPLETED;
			
			//obtaining the parsed CODETABLE object list
			lsCODETABLE = handler_CODETABLE.getLsCODETABLE();

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
		SQLLoader_CODETABLE loader = new SQLLoader_CODETABLE(logger);
		loader.perfomSync(lsCODETABLE);
		
		// return result
		iStatus = this.JOB_COMPLETED;
	}


}
