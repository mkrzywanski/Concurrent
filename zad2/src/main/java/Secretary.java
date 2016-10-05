import org.apache.log4j.Logger;

/**
 * Created by Michał on 04.10.2016.
 */
public class Secretary extends Thread {
    private final static Logger logger = Logger.getLogger(Secretary.class);
    private Storage storage;
    private int totalMessagesNumber;

    public Secretary(Storage storage, int totalMessagesNumber){

        this.storage = storage;
        this.totalMessagesNumber = totalMessagesNumber;
    }
    public void run(){
        while(storage.totalMessagesSent() < totalMessagesNumber) {
            try {
                for (int i = 0; i < storage.getLocationsNumber(); i++) {
                    storage.receiveMessage(i);
                    sleep(500);
                }
            }catch(InterruptedException ex){}
        }

        logger.info("SECRETARY RECEIVED " + storage.totalMessagesSent() +" MESSAGES");
        for(int i = 0; i < storage.getLocationsNumber(); i++){
            logger.info("MANAGER no" + i + " SENT " + storage.locationSentMessages(i) + " MESSAGES");
        }
    }
}
