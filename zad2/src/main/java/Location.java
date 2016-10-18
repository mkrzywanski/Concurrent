import org.apache.log4j.Logger;

/**
 * Created by Michał on 04.10.2016.
 */
public class Location {

    private static final Logger logger = Logger.getLogger(Location.class);
    private Semaphore semaphore;
    private Semaphore sentMsgsSemaphore;
    private Semaphore receivedMsgsSemaphore;

    private Boolean isEmpty = true;
    private int id;
    private String message;
    private int sentMessages;
    private int receivedMessages;

    public Location(int id) {
        this.id = id;
        isEmpty = true;
        semaphore = new Semaphore();
        sentMsgsSemaphore = new Semaphore();
        receivedMsgsSemaphore = new Semaphore();
    }

    public void putMessage(String message) {
        semaphore.acquire();

        if (this.isEmpty) {
            sentMsgsSemaphore.acquire();
            logger.info("Manager number: " + this.id + " sent message MESSAGE: " + message);
            this.message = message;
            this.isEmpty = false;
            this.sentMessages++;
            sentMsgsSemaphore.release();
        }
        semaphore.release();
    }

    public void receiveMessage() {
        semaphore.acquire();

        if (!this.isEmpty) {
            receivedMsgsSemaphore.acquire();
            this.message = "";
            this.isEmpty = true;
            receivedMessages++;
            logger.info("Secretary received message from location: " + this.id + " MESSAGE: " + this.message);
            receivedMsgsSemaphore.release();
        } else {
            logger.info("Secretary skipping location: " + this.id + " (no message)");
        }
        semaphore.release();
    }

    public int getSentMessages() {
        int temp;
        sentMsgsSemaphore.acquire();
        temp = sentMessages;
        sentMsgsSemaphore.release();
        return temp;
    }

    public int getReceivedMessages() {
        int temp;
        receivedMsgsSemaphore.acquire();
        temp = receivedMessages;
        receivedMsgsSemaphore.release();
        return temp;
    }
}
