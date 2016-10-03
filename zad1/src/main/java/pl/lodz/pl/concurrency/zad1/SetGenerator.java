package pl.lodz.pl.concurrency.zad1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SetGenerator {
	private List<Integer> allNumbers = new ArrayList<Integer>();
	private List<Integer> lowerNumbers = new ArrayList<Integer>();
	private List<Integer> higherNumbers = new ArrayList<Integer>();
 	
	public SetGenerator(int lowerBorder, int higherBorder) {
		for(int i = lowerBorder; i<= higherBorder;i++)
			allNumbers.add(i);
		
		Collections.shuffle(allNumbers);
	}
	
	public void generateSets(int lowerSetNumbersAmount) {
		for(int i = 0; i < lowerSetNumbersAmount; i++)
			lowerNumbers.add(allNumbers.get(i));
		for(int i = lowerSetNumbersAmount; i < allNumbers.size(); i++)
			higherNumbers.add(allNumbers.get(i));
	}
	
	public List<Integer> getLowerNumbers() {
		return lowerNumbers;
	}
	
	public List<Integer> getHigherNumbers() {
		return higherNumbers;
	}
	
	public List<Integer> getAllNumbersList() {
		List<Integer> dest= new ArrayList<Integer>(allNumbers);
		Collections.sort(dest);
		return dest;
	}
}
