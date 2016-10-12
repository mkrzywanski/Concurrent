/**
 * Created by Michał on 04.10.2016.
 */
public class Manager extends Thread {

    private int id;
    private Storage storage;
    private int messagesNumber;

    public Manager(int id, Storage storage, int messagesNumber){
        this.id = id;
        this.storage = storage;
        this.messagesNumber = messagesNumber;
    }

    public void run(){
        while(storage.locationSentMessages(id) < messagesNumber){
            try {
                storage.sendMessage(id, "something " + id);
                int delay = (int)(Math.random() * 1000 + 3000);
                sleep(delay);
            }catch (InterruptedException ex){}
        }
    }
}
