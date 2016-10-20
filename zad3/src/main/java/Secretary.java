import org.apache.log4j.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by Michał on 04.10.2016.
 */
public class Secretary extends Thread {
    private final static Logger logger = Logger.getLogger(Secretary.class);
    private int totalMessagesNumber;
    private int receivedMessage;
    private int managersCount;

    private SynchronousQueue<Dispatch> queue;
    private SynchronousQueue<Dispatch> responses;

    public Secretary(int totalMessagesNumber,
                     int managersCount,
                     SynchronousQueue<Dispatch> queue,
                     SynchronousQueue<Dispatch> responses){

        this.totalMessagesNumber = totalMessagesNumber;
        this.managersCount = managersCount;
        this.queue = queue;
        this.responses = responses;
    }
    public void run(){
        while(receivedMessage < totalMessagesNumber) {
            try {
                for (int i = 0; i < managersCount; i++) {

                    Dispatch request = new Dispatch(DispatchType.RECEIVE_MESSAGE, i);
                    queue.put(request);
                    logger.info("Sent request for message");
                    Dispatch response = responses.take();
                    String message;
                    if(response.data!=null){
                        message = (String)response.data;
                        receivedMessage++;
                    }
                    else
                        message = "No message";
                    logger.info("Received response for location: " + i + "  " + message);
                    sleep(1000);
                }

            }catch(InterruptedException ex){}
        }
        logger.info("I received " + receivedMessage + " messages. Shutting down.");
    }
}
