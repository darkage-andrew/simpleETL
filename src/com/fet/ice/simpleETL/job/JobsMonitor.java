package com.fet.ice.simpleETL.job;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JobsMonitor implements Runnable {

    private ThreadPoolExecutor executor;
    private int seconds;
    private boolean bRunning=true;
    private Logger logger;
    private static final String jobName = "Jobs Monitor";
	
	
	public JobsMonitor(ThreadPoolExecutor executor, int delay) {
        this.executor = executor;
        this.seconds = delay;
		
        logger = LogManager.getLogger("JobsMonitor");
		logger.info("Jobs Monitor started -----------------------------------------------------------");
	}

	
    public void shutdown(){
        this.bRunning=false;
		logger.info("Jobs Monitor stoped ------------------------------------------------------------");
    }
	
	
	@Override
	public void run() {

		while(bRunning){			
			logger.debug(
                    String.format("[JobMonitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
                        this.executor.getPoolSize(),
                        this.executor.getCorePoolSize(),
                        this.executor.getActiveCount(),
                        this.executor.getCompletedTaskCount(),
                        this.executor.getTaskCount(),
                        this.executor.isShutdown(),
                        this.executor.isTerminated()));	
			
            try {
                Thread.sleep(seconds*1000);
            } catch (InterruptedException e) {
                logger.info(printStackTrace(e));
            }
		}
		
	}

	
	/**
	 * 
	 * 
	 * @param ex
	 *            Throwable
	 * @return
	 */
	public String printStackTrace(Throwable ex) {
		StringWriter swriter = new StringWriter();
		PrintWriter pwriter = new PrintWriter(swriter);
		ex.printStackTrace(pwriter);
		String stackTrace = swriter.toString();
		try {
			swriter.close();
			pwriter.close();
		} catch (IOException ioe) {
		}
		return stackTrace;
	}	
	
	public String toString() {
		return jobName;
	}

}
