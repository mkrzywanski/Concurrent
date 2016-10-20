import org.apache.log4j.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by Michał on 04.10.2016.
 */
public class Manager extends Thread {
    private final static Logger logger = Logger.getLogger(Manager.class);
    private int id;
    private int messagesNumber;
    private int sentMessages;

    private SynchronousQueue<Dispatch> queue;
    private SynchronousQueue<Dispatch> responses;

    public Manager(int id,
                   int messagesNumber,
                   SynchronousQueue<Dispatch> queue,
                   SynchronousQueue<Dispatch> responses) {
        this.id = id;
        this.messagesNumber = messagesNumber;
        this.queue = queue;
        this.responses = responses;
    }

    public void run() {
        try {
            //sleep(2000);
            while (sentMessages < messagesNumber) {

                Message msg = new Message(this.id, "Cool message from " + this.id);
                Dispatch request = new Dispatch(DispatchType.SEND_MESSAGE, msg);


                queue.put(request);
                logger.info("id: " + this.id + " Sent request.");
                Dispatch response = responses.take();
                logger.info("id: " + this.id + " Received response: " + response.data);
                if (response.data != null)
                    sentMessages++;

                int delay = (int) (Math.random() * 1000 + 3000);
                sleep(delay);

            }
        } catch (InterruptedException ex) {
        }
        logger.info("id: " + this.id + " I sent " + sentMessages + " messages. Shutting down.");
    }
}
