package pl.lodz.pl.concurrency.zad1;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

public class App {
	private final static Logger logger = Logger.getLogger(App.class);
	
	private final static int lowerBorder = 0;
	private final static int higherBorder = 99;
	private final static int lowerSetCapacity = 40;
	
    public static void main( String[] args ) throws InterruptedException {
    	
    	logger.info("Starting program ! " + "Left border: " + lowerBorder + " Right border: " + higherBorder + 
    			"Lower numbers set capacity : " + lowerSetCapacity);
    	
    	SetGenerator setGenerator= new SetGenerator(lowerBorder, higherBorder);
    	setGenerator.generateSets(lowerSetCapacity);
    	
    	List<Integer> lowerSet = setGenerator.getLowerNumbers();
    	List<Integer> highSet = setGenerator.getHigherNumbers();
    	List<Integer> allNumbers = setGenerator.getAllNumbersList();
    	
    	logger.info("All numbers :" + Arrays.toString(allNumbers.toArray()));
    	logger.info("Lower numbers :" + Arrays.toString(lowerSet.toArray()) + " Amount : " + lowerSet.size());
    	logger.info("High numbers :" + Arrays.toString(highSet.toArray()) + " Amount : " + highSet.size());
    	
    	
    	Semaphore semaphore = new Semaphore();
    	LowerSet lowerSetThread = new LowerSet(allNumbers, lowerSet, semaphore);
    	HighSet highSetThread = new HighSet(allNumbers, highSet, semaphore);
    	
    	highSetThread.setLowerSetThread(lowerSetThread);
    	lowerSetThread.setHighSetThread(highSetThread);
    	
    	lowerSetThread.start();
    	highSetThread.start();
    	
    	lowerSetThread.join();
    	highSetThread.join();
    	
    	logger.info(Arrays.toString(lowerSet.toArray()));
    	logger.info(Arrays.toString(highSet.toArray()));
    }
}
