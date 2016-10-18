
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michał on 04.10.2016.
 */
public class Storage {
    private List<Location> locations;
    private Semaphore semaphore;

    public Storage(int locationsNumber) {
        this.initializeLocations(locationsNumber);
        semaphore = new Semaphore();
    }

    public void receiveMessage(int id) {

        locations.get(id).receiveMessage();
    }

    public void sendMessage(int id, String message) {
        semaphore.acquire();
        locations.get(id).putMessage(message);
        semaphore.release();
    }

    public int getLocationsNumber() {
        return this.locations.size();
    }

    public int totalMessagesSent() {
        int total = 0;
        for (Location location : locations) {
            total += location.getReceivedMessages();
        }
        return total;
    }

    public int locationSentMessages(int id) {
        return locations.get(id).getSentMessages();
    }

    private void initializeLocations(int locationsNumber) {
        this.locations = new ArrayList<Location>();
        for (int i = 0; i < locationsNumber; i++) {
            locations.add(new Location(i));
        }
    }

}
