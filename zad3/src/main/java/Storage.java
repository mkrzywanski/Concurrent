
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by Michał on 04.10.2016.
 */
public class Storage extends Thread {
    private final static Logger logger = Logger.getLogger(Storage.class);
    private SynchronousQueue<Dispatch> queue;
    private List<Location> locations;
    private SynchronousQueue<Dispatch> responses;
    private int totalMessages;
    private int sentMessages;
    private int receivedMessages;


    public Storage(int totalMessages,
                   int locationsNumber,
                   SynchronousQueue<Dispatch> queue,
                   SynchronousQueue<Dispatch> responses) {
        this.initializeLocations(locationsNumber);
        this.totalMessages = totalMessages;
        this.queue = queue;
        this.responses=responses;
    }

    public void run() {
        while(receivedMessages < totalMessages || sentMessages < totalMessages) {
            try {
                Dispatch request = queue.take();
                switch(request.type){
                    case SEND_MESSAGE:
                        sendMessage((Message) request.data);
                        break;
                    case RECEIVE_MESSAGE:
                        receiveMessage((Integer)request.data);
                        break;
                    default:
                        break;
                }
            } catch (InterruptedException ex) {
            }
        }
    }

    private void sendMessage(Message msg) throws InterruptedException{
        Boolean isAdded = locations.get(msg.id).putMessage(msg.content);
        if(isAdded)
            sentMessages++;
        responses.put(new Dispatch(DispatchType.SEND_MESSAGE_RESULT,
                isAdded ? "Message sent successfully!" : "MessageBox is full"));
    }

    private void receiveMessage(int id) throws InterruptedException{
        String msg = locations.get(id).receiveMessage();
        if(msg!=null)
            receivedMessages++;
        responses.put(new Dispatch(DispatchType.RECEIVE_MESSAGE_RESULT, msg));
    }

    private void initializeLocations(int locationsNumber) {
        this.locations = new ArrayList<Location>();
        for (int i = 0; i < locationsNumber; i++) {
            locations.add(new Location(i));
        }
    }

}
