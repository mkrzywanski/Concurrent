package pl.lodz.pl.concurrency.zad1;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

public class HighSet extends Thread {
	private final static Logger logger = Logger.getLogger(HighSet.class);
	
	private List<Integer> allNumbers;
	private List<Integer> currentHighNumbers;
	private Integer highNumberSize;
	
	private LowerSet lowerSetThread;
	private Semaphore semaphore;
	
	public HighSet(List<Integer> allNumbers, List<Integer> currentHighNumbers, Semaphore semaphore) {
		this.allNumbers = allNumbers;
		this.currentHighNumbers = currentHighNumbers;
		Collections.sort(this.currentHighNumbers);
		this.highNumberSize = currentHighNumbers.size();
		this.semaphore = semaphore;
	}
	
	public void run(){
		while(true) { 
				semaphore.acquire();
				logger.info("SEMAPHORE ACQUIRED");
				Integer currentHigherNumbersSetSize = currentHighNumbers.size();
				if(currentHigherNumbersSetSize.equals(highNumberSize - 1 )) {
					releaseSemaphore();
					continue;
				}
				
				Collections.sort(this.currentHighNumbers);
				
				if(highNumberSize.equals(currentHighNumbers.size()) && checkIfDone()) {
					releaseSemaphore();
					break;
				}
				logger.info("ADDING LOWEST NUMBER TO LOWERSET");
				lowerSetThread.add(currentHighNumbers.get(0));
				currentHighNumbers.remove(0);
				
				releaseSemaphore();

		}
	}
	
	private boolean checkIfDone() {
		for(int c = 0, a = allNumbers.size() - currentHighNumbers.size();c < currentHighNumbers.size();c++, a++ ) {
			if(!allNumbers.get(a).equals(currentHighNumbers.get(c))) {
				return false;
			}
		}
		return true;
	}
	
	private void releaseSemaphore() {
		this.semaphore.release();
		logger.info("SEMAPHORE RELEASED");
	}
	
	public void setLowerSetThread(LowerSet lowerSetThread) {
		this.lowerSetThread = lowerSetThread;
	}
	
	public void add(Integer number) {
		this.currentHighNumbers.add(number);
	}
	
	
}
