package com.fet.ice.simpleETL.job;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.logging.log4j.Logger;

public class RejectedExecutionHandlerImpl implements RejectedExecutionHandler{

	private Logger logger;
	
	public RejectedExecutionHandlerImpl(Logger logger) {
		// TODO Auto-generated constructor stub
	}
	
    @Override
    public void rejectedExecution(Runnable job, ThreadPoolExecutor executor) {
        logger.debug(job.toString() + " - is rejected by threadpool.");
    }

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

    
    
    
}
