import org.apache.log4j.Logger;
import org.omg.CORBA.BooleanHolder;

/**
 * Created by Michał on 04.10.2016.
 */
public class Location {

    private static final Logger logger = Logger.getLogger(Location.class);

    private Boolean isEmpty = true;
    private int id;
    private String message;
    private int sentMessages;
    private int receivedMessages;

    public Location(int id) {
        this.id = id;
        isEmpty = true;
    }

    public Boolean putMessage(String message) {
        if (this.isEmpty) {
            this.message = message;
            this.isEmpty = false;
            this.sentMessages++;
            return true;
        }
        else
            return false;
    }

    public String receiveMessage() {
        if (!this.isEmpty) {
            String result = this.message;
            this.message = "";
            this.isEmpty = true;
            receivedMessages++;
            return result;
        }
        else
            return null;
    }

    public int getSentMessages() {
        return this.sentMessages;
    }

    public int getReceivedMessages() {
        return this.receivedMessages;
    }
}
