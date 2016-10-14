package pl.lodz.pl.concurrency.zad1;

public class Semaphore {
	
	private boolean signal = false;
	
	public synchronized void acquire() {
		while(this.signal) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.signal = true;
	}
	
	public synchronized void release(){
		this.signal = false;
		notifyAll();
	}

}
