package pl.lodz.pl.concurrency.ConcurrentBanker;

import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;

public class Banker {

	private static Logger logger = Logger.getLogger(Banker.class);
	private static int rejectedMaxValue = 5;
	private Semaphore semaphore = new Semaphore(1);

	private int maxCapital;
	private int currentCapital;
	private Integer clientIdWhoBankerIsWaitingFor;

	public Banker(int maxCapital) {
		this.maxCapital = maxCapital;
		this.currentCapital = maxCapital;
	}

	public void meetClient(Client client) {
		try {
			semaphore.acquire();
			logger.info("Bank capital before client came: " + currentCapital + " florens.");
			logger.info("Client " + client.getId() + " entered the bank");
			
			if (client.getCurrentLoan() == 0) {
				int newLoan = Client.getRandomLoan(maxCapital);
				
				if (clientIdWhoBankerIsWaitingFor != null && !clientIdWhoBankerIsWaitingFor.equals(client.getId())) {
					logger.info("Cannot make the operation because bank is waiting for the money from client with id "
							+ clientIdWhoBankerIsWaitingFor);
					if(client.getWantedLoan() == null) {
						client.setWantedLoan(newLoan);
					}
				} else {
					if(client.getWantedLoan() != null) {
						newLoan = client.getWantedLoan();
					}
					logger.info("Client wants to take a loan: " + newLoan);
					if (currentCapital >= newLoan) {
						logger.info("Accepted");
						currentCapital -= newLoan;
						client.setCurrentLoan(newLoan);
						if (client.getRejectedCounter() >= rejectedMaxValue) {
							clientIdWhoBankerIsWaitingFor = null;
						}
						client.setRejectedCounter(0);
						client.setWantedLoan(null);
						client.incrementServiceCounter();
					} else {
						client.setRejectedCounter(client.getRejectedCounter() + 1);
						if (client.getRejectedCounter() == rejectedMaxValue) {
							clientIdWhoBankerIsWaitingFor = client.getId();
						}
						if(client.getWantedLoan() == null) {
							client.setWantedLoan(newLoan);
						}
						logger.info("Rejected");
					}
				}
			} else {
				logger.info("Client with id " + client.getId() + " wants to give back " + client.getCurrentLoan());
				currentCapital += client.getCurrentLoan();
				client.setCurrentLoan(0);
			}
			logger.info("Bank capital after operation: " + currentCapital + " florens.\n");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}
	}

	public int getMaxCapital() {
		return maxCapital;
	}

	public void setMaxCapital(int maxCapital) {
		this.maxCapital = maxCapital;
	}

	public int getCurrentCapital() {
		return currentCapital;
	}

	public void setCurrentCapital(int currentCapital) {
		this.currentCapital = currentCapital;
	}

	public Integer getClientIdWhoBankerIsWaitingFor() {
		return clientIdWhoBankerIsWaitingFor;
	}

	public void setClientIdWhoBankerIsWaitingFor(Integer clientIdWhoBankerIsWaitingFor) {
		this.clientIdWhoBankerIsWaitingFor = clientIdWhoBankerIsWaitingFor;
	}

}
