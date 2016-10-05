import org.apache.log4j.Logger;

/**
 * Created by Michał on 04.10.2016.
 */
public class Location {

    private static final Logger logger = Logger.getLogger(Location.class);
    private Semaphore semaphore;

    private Boolean isEmpty = true;
    private int id;
    private String message;
    private int sentMessages;

    public Location (int id){
        this.id = id;
        isEmpty = true;
        semaphore = new Semaphore();
        sentMessages = 0;
    }

    public void putMessage(String message){
        semaphore.acquire();
        if(this.isEmpty){
            logger.info("Manager number: " + this.id + " sent message MESSAGE: " + message);
            this.message = message;
            this.isEmpty = false;
            this.sentMessages++;
        }
        semaphore.release();
    }

    public void receiveMessage(){
        semaphore.acquire();
        if(!this.isEmpty) {
            logger.info("Secretary received message from location: " + this.id + " MESSAGE: " + this.message);
            this.message = "";
            this.isEmpty = true;
        }
        else{
            logger.info("Secretary skipping location: " + this.id + " (no message)");
        }
        semaphore.release();
    }

    public int getSentMessages(){
        return this.sentMessages;
    }
}
