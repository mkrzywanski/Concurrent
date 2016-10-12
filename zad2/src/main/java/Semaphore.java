import org.apache.log4j.Logger;

/**
 * Created by Michał on 05.10.2016.
 */
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
