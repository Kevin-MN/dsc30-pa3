/*
  Name: Kevin Morales-Nguyen
  PID:  A17186624
 */
import java.util.ArrayList;

/**
 * This is the abstract class User, declares abstarct methods to be implemented by
 * base classes and defines some of the methods that can be used universaly among
 * subclasses
 * @author Kevin Morales-Nguyen
 * @since  10/18/21
 */
public abstract class User {

    // Error message to use in OperationDeniedException
    protected static final String JOIN_ROOM_FAILED =
            "Failed to join the chat room.";
    protected static final String INVALID_MSG_TYPE =
            "Cannot send this type of message to the specified room.";

    // instance variables
    protected String username;
    protected String bio;
    protected ArrayList<MessageExchange> rooms;

    /**
     * This is the main constructor that defines universal attributes
     * and initializes them
     * @param username the username of the user
     * @param bio small biography of the user
     * @throws IllegalArgumentException if username or bio are null
     */
    public User(String username, String bio) {
        if(username == null || bio == null) {
            throw new IllegalArgumentException();
        }

        this.username = username;
        this.bio = bio;
        this.rooms = new ArrayList<MessageExchange>();

    }

    /**
     * This is a "dummy" default constructor that initializes all attributes
     * to null, compiler gives error without it
     */
    User(){
        this.username = null;
        this.bio = null;
        this.rooms = null;
    }

    /**
     * This method sets a new bio to the bio attribute
     * @param newBio string that holds new bio
     * @throws IllegalArgumentException if newBio is bull
     */
    public void setBio(String newBio) {
        if(newBio == null){
            throw new IllegalArgumentException();
        }
        this.bio = newBio;
    }

    /**
     * This method returns the bio attribute
     * @return bio instance variable
     */
    public String displayBio() {
        return this.bio;
    }

    /**
     * This method is used to join a User to a message exchange
     * @param me the massage exchange for the user to join
     * @throws OperationDeniedException if the user is already part of the room, or joining
     * failed as specified form addUser() in MessageExchange
     * @throws IllegalArgumentException if me is null
     */
    public void joinRoom(MessageExchange me) throws OperationDeniedException {

        if(me == null){
            throw new IllegalArgumentException();
        }

        if(this.rooms.contains(me)){
            throw new OperationDeniedException(JOIN_ROOM_FAILED);
        }

        if(!me.addUser(this)){
            throw new OperationDeniedException(JOIN_ROOM_FAILED);
        }

        this.rooms.add(me);

    }

    /**
     * This method allows the user to leave a room and removes them from the rooms attribute
     * and the users arraylist in the messageexhange attribute variable
     * @param me the message exchange to be removed from
     * @throws IllegalArgumentException if me is null
     */
    public void quitRoom(MessageExchange me) {

        if(me == null){
            throw new IllegalArgumentException();
        }

        if(!me.getUsers().contains(this)){
            return;
        }

        this.rooms.remove(me);
        me.getUsers().remove(this);

    }

    /**
     * This method allows the user to send a message to a message exchage and also adds it to
     * the log to be processed by a tutor
     * @param me the message exhange to send the message to
     * @param contents the contents of thste string
     * @param lines how many lines of code if CodeMessage, -1 to indicate text message
     * @throws IllegalArgumentException if me or contents are null, if the user is not part of the room
     */
    public void sendMessage(MessageExchange me, String contents, int lines) {
        if(me == null || contents == null){
            throw new IllegalArgumentException();
        }

        if(!this.rooms.contains(me)){
            throw new IllegalArgumentException();
        }

        try{
            if(lines == -1){
                Message msg_to_send = new TextMessage(this, contents);
                me.recordMessage(msg_to_send);
            }
            else{
                Message msg_to_send = new CodeMessage(this, contents, lines);
                me.recordMessage(msg_to_send);
            }


        }
        catch(OperationDeniedException exception){
            System.out.println(exception.getMessage());
        }
        catch(IllegalArgumentException exception){
            System.out.println(exception.getMessage());
        }


    }

    /**
     * abstract method to be implemented in subclasses, tutors and students
     * fetch mehtods differently
     * @param me the message exchange to fetch messages from
     * @return a string with a aggregate of the latest messages
     */
    public abstract String fetchMessage(MessageExchange me);

    /**
     * This is an abstarct method that would display names for users,
     * tutors and students display names differently
     * @return a formated string that displays the users name
     */
    public abstract String displayName();

}
