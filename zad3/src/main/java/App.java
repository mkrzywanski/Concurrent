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
        SynchronousQueue<Dispatch> requests = new SynchronousQueue<Dispatch>();
        SynchronousQueue<Dispatch> responses = new SynchronousQueue<Dispatch>();

        new Storage(MESSAGES_PER_MANAGER * NUMBER_OF_MANAGERS, NUMBER_OF_MANAGERS, requests, responses).start();

        for (int i = 0; i < NUMBER_OF_MANAGERS; i++) {
            new Manager(i, MESSAGES_PER_MANAGER, requests, responses).start();
        }
        new Secretary( MESSAGES_PER_MANAGER * NUMBER_OF_MANAGERS, NUMBER_OF_MANAGERS, requests, responses).start();

    }
}
