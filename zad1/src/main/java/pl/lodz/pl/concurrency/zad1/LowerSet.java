package pl.lodz.pl.concurrency.zad1;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

public class LowerSet extends Thread {
	
	final static Logger logger = Logger.getLogger(LowerSet.class);
	
	private List<Integer> allNumbers;
	private List<Integer> currentLowNumbers;
	private Integer lowNumberSize;
	
	private HighSet highSetThread;
	private Semaphore semaphore;
	
	public LowerSet(List<Integer> allNumbers, List<Integer> currentLowerNumbers, Semaphore semaphore) {
		this.allNumbers = allNumbers;
		this.currentLowNumbers = currentLowerNumbers;
		Collections.sort(this.currentLowNumbers);
		this.lowNumberSize = currentLowerNumbers.size();
		this.semaphore = semaphore;
	}
	
	public void run(){
		while(true) {
				semaphore.acquire();
				logger.info("SEMAPHORE ACQUIRED");
				Integer currentLowerNumbersSetSize = currentLowNumbers.size();
				if(currentLowerNumbersSetSize.equals(lowNumberSize - 1)){
					releaseSemaphore();
					continue;
				}
				
				Collections.sort(this.currentLowNumbers);
				
				if(lowNumberSize.equals(currentLowNumbers.size()) && checkIfDone()) {
					releaseSemaphore();
					break;
				}
				
				logger.info("ADDING HIGHEST NUMBER TO HIGHERSET");
				highSetThread.add(currentLowNumbers.get(currentLowNumbers.size() - 1));
				currentLowNumbers.remove(currentLowNumbers.size() - 1);
				
				releaseSemaphore();
		
		}
	}
	
	private boolean checkIfDone() {
		for(int i = 0;i < currentLowNumbers.size();i++ ) {
			if(!allNumbers.get(i).equals(currentLowNumbers.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	private void releaseSemaphore() {
		semaphore.release();
		logger.info("SEMAPHORE RELEASED");
	}
	
	public void setHighSetThread(HighSet highSetThread) {
		this.highSetThread = highSetThread;
	}
	
	public void add(Integer number) {
		currentLowNumbers.add(number);
	}
	
}
