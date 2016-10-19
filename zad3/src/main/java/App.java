import org.apache.log4j.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by Michał on 04.10.2016.
 */
public class App {
    private final static Logger logger = Logger.getLogger(App.class);
    private final static int MESSAGES_PER_MANAGER = 10;
    private final static int NUMBER_OF_MANAGERS = 2;

    public static void main(String[] args) {
        logger.info("Starting program !");
        CountDownLatch signal = new CountDownLatch(1);
        SynchronousQueue<Dispatch> dispatchQueue = new SynchronousQueue<Dispatch>();


        for (int i = 0; i < NUMBER_OF_MANAGERS; i++) {
            new Manager(i, MESSAGES_PER_MANAGER, dispatchQueue, signal).start();
        }
        new Secretary( MESSAGES_PER_MANAGER * NUMBER_OF_MANAGERS, NUMBER_OF_MANAGERS, dispatchQueue, signal).start();
        new Storage(MESSAGES_PER_MANAGER * NUMBER_OF_MANAGERS, NUMBER_OF_MANAGERS, dispatchQueue, signal).run();
    }
}
