/*
  Name: Kevin Morales-Nguyen
  PID:  A17186624
 */
import java.util.ArrayList;


/**
 * This is the subclass implementation of a Student, derived from the user
 * superclass, it fetches messages and displays names differently
 * @author Kevin Morales-Nguyen
 * @since  10/18/21
 */
public class Student extends User {

    // Message to append when fetching non-text message
    private static final String FETCH_DENIED_MSG =
            "This message cannot be fetched because you are not a premium user.";

    // max number of messages to fetch
    private static final int MAX_MSG_SIZE = 100;

    /**
     * This is the main constructor used to create Student objects, it just
     * uses super()
     * @param username username of user
     * @param bio biography of user
     */
    public Student(String username, String bio) {
        super(username, bio);
    }

    /**
     * This method is used to gather the lastest messages from a message exchange
     * it grabs the log from the exhange and then appends it to a single string
     * approrpiatly based on the message type and whether it is a tutor or
     * student making the request
     *
     * @param me the message exchange to fetch the messaged from
     * @return a String that appends messages together from the
     * message exchange log
     * @throws IllegalArgumentException if me is null or user is the user is not part
     * of the message exchange
     */
    public String fetchMessage(MessageExchange me) {
        if(me == null){
            throw new IllegalArgumentException();
        }

        if(!this.rooms.contains(me)){
            throw new IllegalArgumentException();
        }

        ArrayList<Message> last_messages = me.getLog(this);

        String aggregate_messages = "";

        for(int i = 0;i < last_messages.size();i++){
            if(last_messages.get(i) instanceof CodeMessage){
                aggregate_messages = aggregate_messages.concat(FETCH_DENIED_MSG + "\n");
            }
            else{
                aggregate_messages = aggregate_messages.concat(last_messages.get(i)
                        .getContents() + "\n");
            }

        }

        return aggregate_messages;
    }

    /**
     * Simple method that returns the students username
     * @return String that contains student's username
     */
    public String displayName() {
        return this.username;
    }
}
