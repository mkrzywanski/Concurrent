import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Michał on 04.10.2016.
 */
public class Storage {
    private Queue<Integer> managersQueue;
    private List<Location> locations;

    public Storage(int locationsNumber){
        locations = new ArrayList<Location>();
        this.initializeLocations(locationsNumber);
        this.managersQueue = new ArrayDeque<Integer>();
    }

    public void receiveMessage(int id){
        locations.get(id).receiveMessage();
    }

    public void sendMessage(int id, String message){
        if(!managersQueue.contains(id))
            managersQueue.add(id);

        if(managersQueue.peek()==id){
            locations.get(id).putMessage(message);
            managersQueue.remove();
        }

    }

    public int getLocationsNumber(){
        return this.locations.size();
    }

    public int totalMessagesSent() {
        int total = 0;
        for(Location location : locations){
            total += location.getSentMessages();
        }
        return total;
    }

    public int locationSentMessages(int id){
        return locations.get(id).getSentMessages();
    }

    private void initializeLocations(int locationsNumber){
        this.locations = new ArrayList<Location>();
        for(int i = 0 ; i < locationsNumber; i++){
            locations.add(new Location(i));
        }
    }


}
