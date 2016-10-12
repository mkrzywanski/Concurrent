import org.apache.log4j.Logger;

/**
 * Created by Michał on 04.10.2016.
 */
public class App {
    private final static Logger logger = Logger.getLogger(App.class);
    private final static int MESSAGES_PER_MANAGER = 10;
    private final static int NUMBER_OF_MANAGERS = 5;

    public static void main( String[] args ){
        logger.info("Starting program !");

        Storage storage = new Storage(NUMBER_OF_MANAGERS);
        for(int i = 0; i < NUMBER_OF_MANAGERS; i++){
            new Manager(i, storage, MESSAGES_PER_MANAGER).start();
        }

        new Secretary(storage, MESSAGES_PER_MANAGER * NUMBER_OF_MANAGERS).start();


    }
}
