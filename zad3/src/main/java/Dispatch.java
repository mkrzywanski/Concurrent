/**
 * Created by Michał on 18.10.2016.
 */
public class Dispatch {
    public final DispatchType type;
    public final Object data;

    public Dispatch(DispatchType type, Object data){
        this.type = type;
        this.data = data;
    }
}

enum DispatchType{
    SEND_MESSAGE,
    SEND_MESSAGE_RESULT,
    RECEIVE_MESSAGE,
    RECEIVE_MESSAGE_RESULT
}
