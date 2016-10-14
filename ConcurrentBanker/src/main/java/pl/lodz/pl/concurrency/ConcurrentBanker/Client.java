package pl.lodz.pl.concurrency.ConcurrentBanker;

import java.util.Random;

public class Client implements Runnable {

	private static int maxSleep = 500;
	
	private Random random = new Random();
	private int currentLoan;
	private int id;
	private int rejectedCounter;
	private int operationLimit;
	private Integer wantedLoan;
	private int serviceCounter;

	private Banker banker;
	
	public Client(int id, int operationLimit, Banker banker) {
		this.id = id;
		this.operationLimit = operationLimit;
		this.banker = banker;
	}
	
	public static int getRandomLoan(int maxLoanValue) {
		return (int) (maxLoanValue * Math.random());
	}

	public void run() {
		while(serviceCounter < operationLimit){
			banker.meetClient(this);
			try {
				Thread.sleep(random.nextInt(maxSleep));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(currentLoan != 0) {
			banker.meetClient(this);
		}
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getRejectedCounter() {
		return rejectedCounter;
	}

	public void setRejectedCounter(int rejectedCounter) {
		this.rejectedCounter = rejectedCounter;
	}
	
	public int getCurrentLoan() {
		return currentLoan;
	}

	public void setCurrentLoan(int currentLoan) {
		this.currentLoan = currentLoan;
	}
	
	public Integer getWantedLoan() {
		return wantedLoan;
	}

	public void setWantedLoan(Integer wantedLoan) {
		this.wantedLoan = wantedLoan;
	}
	
	public int getServiceCounter() {
		return serviceCounter;
	}
	public void incrementServiceCounter() {
		serviceCounter++;
	}

	
}
