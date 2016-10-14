package pl.lodz.pl.concurrency.ConcurrentBanker;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class App {
	
	private static Logger logger = Logger.getLogger(App.class);
	
	private static int clientsAmount = 5;
	private static int bankerMaxCapital = 1000;
	private static int clientOperationLimit = 5;
	
    public static void main( String[] args ) throws InterruptedException {
    	logger.info("Starting program");
    	logger.info("Clients: " + clientsAmount + " Banker's max capital: " + bankerMaxCapital + " Client's loan operation limit: " + clientOperationLimit);
    	
        Banker banker = new Banker(bankerMaxCapital);
        List<Client> clients = new ArrayList<Client>();
        List<Thread> threads = new ArrayList<Thread>();
        
        for(int i = 0; i < clientsAmount; i++) {
        	clients.add(new Client(i, clientOperationLimit, banker));
        	threads.add(new Thread(clients.get(i)));
        	threads.get(i).start();
        }
        
        for(int i = 0; i < clientsAmount; i++) {
        	threads.get(i).join();
        }
        	
        
        for(int i = 0; i < clientsAmount; i++) {
        	Client client = clients.get(i);
        	logger.info("Client with id " + client.getId() + " currentLoan: " + client.getCurrentLoan());
        }
        logger.info("Banker's capital: " + banker.getCurrentCapital());
        
    }
}
