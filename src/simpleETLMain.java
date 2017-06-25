import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fet.ice.simpleETL.extract.CODETABLE_Extractor;
import com.fet.ice.simpleETL.extract.ProductData_Extractor;
import com.fet.ice.simpleETL.extract.PromotionData_Extractor;
import com.fet.ice.simpleETL.job.JobsMonitor;
import com.fet.ice.simpleETL.job.RejectedExecutionHandlerImpl;

/**
 * 
 */

/**
 * 2017/06/09 �إ�
 * <p>
 * Title: com.fet.ice.simpleETL Main program
 * </p>
 * <p>
 * Description: �C���ƦP�B�{��
 * </p>
 * <p>
 * Copyright: (c) Copyright HP Corp. 2017. All Rights Reserved.
 * </p>
 * 
 * $Id: DBSyncMain.java 135 2017-06-09 06:51:30Z andrew $ $Revision: 135 $
 * $Author: Andrew $ $Date: 2017-06-09 14:51:30 +0800 (Thu, 09 Jun 2017) $
 */
public class simpleETLMain {

	private static final int PARALLEL_MODE = 1;
	private static final int SEQUENTIAL_MODE = 2;

	private static Logger logger;
	private static Properties prop;
	private static int running_mode = SEQUENTIAL_MODE; // default running mode

	/**
	 * 
	 */
	public simpleETLMain() {
		
		logger = LogManager.getLogger(this.getClass().getName());
		
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
			return;
		} catch (IOException e) {
			logger.debug("IOException: simpleETL.properties");
			return;
		}
	}

	private static void runParallel(ThreadPoolExecutor executorPool) {

		// CODATABLE tested ok
		CODETABLE_Extractor extractor_CODETABLE = new CODETABLE_Extractor("CODE_TABLE extractor", logger,
				prop.getProperty("CODETABLE_XML_FILEPATH"));
		executorPool.execute(extractor_CODETABLE);

		// PRODUCTDATA tested ok
		ProductData_Extractor extractor_PRODUCTDATA = new ProductData_Extractor("PRODUCT DATA extractor", logger,
				prop.getProperty("PRODUCTDATA_XML_FILEPATH"));
		executorPool.execute(extractor_PRODUCTDATA);

		// PROMOTIONDATA tested ok
		PromotionData_Extractor extractor_PROMOTIONDATA = new PromotionData_Extractor("PROMOTION DATA extractor",
				logger, prop.getProperty("PROMOTIONDATA_XML_FILEPATH"));
		executorPool.execute(extractor_PROMOTIONDATA);

	}

	private static void runSequential() {
			
		// CODATABLE tested ok
/*		CODETABLE_Extractor extractor_CODETABLE = new CODETABLE_Extractor("CODE_TABLE extractor", logger,
				prop.getProperty("CODETABLE_XML_FILEPATH"));
		extractor_CODETABLE.run();
*/
		// PRODUCTDATA tested ok
/*		ProductData_Extractor extractor_PRODUCTDATA = new ProductData_Extractor("PRODUCT DATA extractor", logger,
				prop.getProperty("PRODUCTDATA_XML_FILEPATH"));
		// extractor_PRODUCTDATA.run();
		extractor_PRODUCTDATA.run();
*/
		// PROMOTIONDATA tested ok
		PromotionData_Extractor extractor_PROMOTIONDATA = new PromotionData_Extractor("PROMOTION DATA extractor",
				logger, prop.getProperty("PROMOTIONDATA_XML_FILEPATH"));
		// extractor_PROMOTIONDATA.run();
		extractor_PROMOTIONDATA.run();
		
	}

	
	private String getRunningMode()
	{		
		switch(running_mode){
		case 1: return "Parallel Mode";
		case 2: return "Sequential Mode";
		}
		
		return "";
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		simpleETLMain batchMain = new simpleETLMain();

		// obtains the start time and logged.
		Timestamp dtStarted = new Timestamp(new java.util.Date().getTime());
		logger.debug("simpleETL Main(): " + batchMain.getRunningMode() + ", starts @ " + dtStarted + " -----------------------------------------------------------------------");

		// 2 options here, parallel & sequential execution
		switch (running_mode) {
		case PARALLEL_MODE:
			// RejectedExecutionHandler implementation
			RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl(logger);

			// leverage threadfactory to create a threadpool
			ThreadFactory threadFactory = Executors.defaultThreadFactory();

			// creating the ThreadPoolExecutor
			ThreadPoolExecutor executorPool = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS,
					new ArrayBlockingQueue<Runnable>(5), threadFactory, rejectionHandler);

			// start up jobs monitoring thread with delay n seconds
			JobsMonitor monitor = new JobsMonitor(executorPool, 5);
			Thread monitorThread = new Thread(monitor);
			monitorThread.start();

			// execute jobs in parallel
			runParallel(executorPool);

			try {
				// shut down jobs monitor
				while (true) {
					if (executorPool.getCompletedTaskCount() == executorPool.getTaskCount()) {
						executorPool.shutdown();
						Thread.sleep(1000);
						monitor.shutdown();
						break;
					}
				}
			} catch (InterruptedException ie) {
				logger.debug(ie.getMessage());
			}

			break;
		case SEQUENTIAL_MODE:
			runSequential();
			break;
		}
		
		// obtains the stop time and logged.
		Timestamp dtFinished = new Timestamp(new java.util.Date().getTime());
		logger.debug("simpleETL Main(): "+ batchMain.getRunningMode() + ", stops @ " + dtFinished + ". Total time consumed - " + (dtFinished.getTime() - dtStarted.getTime()) + " ms. ---------------------------------------");
	}

}
