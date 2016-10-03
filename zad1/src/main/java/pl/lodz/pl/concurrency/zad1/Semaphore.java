package pl.lodz.pl.concurrency.zad1;

import org.apache.log4j.Logger;

public class Semaphore {
	
	private static final Logger logger = Logger.getLogger(Semaphore.class);
	private boolean signal = false;
	
	public void acquire() {
		while(this.signal){
			logger.info("SEMAPHORE WAITING");
		}
	    this.signal = true;
	}

	public void release() {
		this.signal = false;
	}

}
