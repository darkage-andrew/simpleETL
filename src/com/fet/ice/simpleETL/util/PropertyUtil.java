package com.fet.ice.simpleETL.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.Logger;

public class PropertyUtil {

	private static PropertyUtil propUtil = new PropertyUtil();

	private static Properties prop;

	private PropertyUtil() {
		// TODO Auto-generated constructor stub
	}

	public static PropertyUtil getPropertyUtil(Logger logger) {
		// read in properties for ETL batch jobs
		prop = new Properties();

		try {
			// for local test, should be remarked for deployment
			FileInputStream fisProp = new FileInputStream(
					"C:/Users/tsaiand/workspace/simpleETL/resource/simpleETL.properties");
			prop.load(fisProp);
			fisProp.close();
		} catch (FileNotFoundException e) {
			logger.debug("FileNotFoundException: simpleETL.properties");
		} catch (IOException e) {
			logger.debug("IOException: simpleETL.properties");
		}

		return propUtil;
	}

	public String getProperty(String sKey) {
		if (prop != null) {
			return prop.getProperty(sKey);
		}

		return null;
	}

}
