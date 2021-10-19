/*
  Name: Kevin Morales-Nguyen
  PID:  A17186624
 */
import com.sun.corba.se.spi.orb.Operation;

import java.util.ArrayList;
import java.util.List;

/**
 * This is class that simulated a Autograder that can be used by students and tutors
 * to resolve questions, it has attributes that keep track of questions, users, and
 * resolved questions. It also allows users to join or leave the message exhange room
 *
 * @author Kevin Morales-Nguyen
 * @since  10/18/21
 */
public class Autograder implements MessageExchange {

    // time allowed
    private static final int DEFAULT_ALLOTTED_TIME = 15;
    private static final int LINES_PER_MINUTE = 10;
    private static final int MAX_LINES = 150;

    // max number of messages to fetch
    private static final int MAX_MSG_SIZE = 100;

    // Error message to use in OperationDeniedException
    protected static final String SESSION_ENDED =
            "Session has already ended. Ticket can't be resolved";
    protected static final String NO_ACCESS =
            "Only tutors can actively resolve tickets.";
    protected static final String NO_LOGS =
            "There are no more messages in the log.";



    // instance variables
    private ArrayList<User> users;
    private ArrayList<Message> log;
    private ArrayList<String> results;
    private Tutor tutor;

    /**
     * This is the main constructor for instantiatting Autograder objects,
     * it also initializes all the attribute arraylists and sets the tutor to the
     * message exchange
     * @param tutor the tutor that is the "owner" of the exchange room
     */
    public Autograder(Tutor tutor) {
        this.users = new ArrayList<User>();
        this.log = new ArrayList<Message>();
        this.results = new ArrayList<String>();
        this.tutor = tutor;
        this.users.add(this.tutor);


    }

    /**
     * This method is used to get the log attribute arraylist that contains messages
     * @param requester The user that requests this operation.
     * @return a arraylist that holds Message objects
     */
    public ArrayList<Message> getLog(User requester) {
        if(this.tutor == null){
            return null;
        }

        if(requester.equals(this.tutor)){
            return this.log;
        }
        else if(this.log.size() < MAX_MSG_SIZE){
            return this.log;
        }
        else{
            ArrayList<Message> return_100_msgs = new ArrayList<Message>();
            for(int i  = 0; i < MAX_MSG_SIZE;i++){
                return_100_msgs.add(this.log.get((this.log.size() - 1) - i));
            }

            return return_100_msgs;
        }

    }

    /**
     * This method returns the results attribute ararylist
     * @return a reference to an arraylist that hold String that
     * contain information about resolved messages
     */
    public ArrayList<String> getResults(){
        if(this.tutor == null){
            return null;
        }
        else{
            return this.results;
        }
    }

    /**
     * This method is used to ensure users are added to the message exchange, and properly
     * updates room attributes for both objects
     * @param u User to add.
     * @return a boolean indicating if the user was added or not
     */
    public boolean addUser(User u) {
        if(this.users.contains(u)){
            return false;
        }
        else if(this.tutor == null){
            return false;
        }
        else{
            this.users.add(u);

            return true;
        }

    }

    /**
     * This method allows a user to be removed from the room based from a requester, it also
     * approrpiatly updates the attributes of both objects
     * @param requester The user that requests this operation.
     * @param u User to remove.
     * @return a boolean indicating if the user was removed from the messsage exchange or not
     */
    public boolean removeUser(User requester, User u) {
        if(this.tutor == null){
            return false;
        }

        if(!this.users.contains(u)){
            return false;
        }

        if(requester.equals(u) && u.equals(this.tutor)){
            return false;
        }
        else if(requester.equals(u) && u instanceof Student){
            this.users.remove(u);
            u.quitRoom(this);
            return true;
        }
        else if(requester instanceof Student && !u.equals(requester)){
            return false;
        }
        else if(requester instanceof Tutor && u instanceof Student){
            this.users.remove(u);
            u.quitRoom(this);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * this method simply returns the user attribute arraylist
     * @return an arraylist that contains objects of type User
     */
    public ArrayList<User> getUsers() {
        if(this.tutor == null){
            return null;
        }
        else{
            return this.users;
        }
    }

    /**
     * this method allows messages to be added to the Autograder, it adds the
     * Message object to the log arraylist
     * @param m Message to add.
     * @return a boolean indicate if the message was added or not
     */
    public boolean recordMessage(Message m) {
        if(this.tutor == null){
            return false;
        }
        else{
            this.log.add(m);
            return true;
        }
    }


    /**
     * This method allows tutors to resolve messages, and also return a string that indicates the
     * resolved/unresolved parts of the Message
     * @param requester the user requesting for the most message to be resolved
     * @return a String that contains onformation about how the message was handled and
     * how it was resolved
     * @throws OperationDeniedException if the session has been stopped, a student is a requester,
     * or there are no messages to process
     */
    public String resolveTicket(User requester) throws OperationDeniedException {
        if(this.tutor == null){
            throw new OperationDeniedException(SESSION_ENDED);
        }

        if(requester instanceof Student){
            throw new OperationDeniedException(NO_ACCESS);
        }

        if(this.log.size() == 0){
            throw new OperationDeniedException(NO_LOGS);
        }



        if(this.log.get(0) instanceof TextMessage){
            this.log.remove(0);
            this.results.add("This ticket doesn’t resolve a codeMessage");
            return "This ticket doesn’t resolve a codeMessage";

        }
        else{
            CodeMessage process_message = (CodeMessage) this.log.remove(0);
            int lines = process_message.getLines();
            int unresolved = 0;
            int resolved = 0;

            if(Math.ceil(lines / LINES_PER_MINUTE) > DEFAULT_ALLOTTED_TIME){
                resolved = lines;
                unresolved = lines - MAX_LINES;
            }
            else{
                resolved = lines;
            }

            String result_ticket = "This ticket resolves " + resolved + " lines, " +
                    unresolved + " lines unresolved";
            this.results.add(result_ticket);
            return result_ticket;
        }

    }

    /**
     * This method stops the grading session by setting the tutor
     * attribute to null, it also clears the attribute arraylists
     * @return a boolean indicating that the tutor attribute has been set to null or not
     */
    public boolean stopSession(){
        if(this.tutor == null){
            return false;
        }
        else{
            this.tutor = null;
            this.log.clear();
            this.log.clear();
            return true;
        }

    }

}