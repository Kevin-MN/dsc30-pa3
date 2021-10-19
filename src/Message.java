/*
  Name: Kevin Morales-Nguyen
  PID:  A17186624
 */
import java.time.LocalDate;

/**
 * Messenger Application Test
 * @author Kevin Morales-Nguyen
 * @since  10/18/21
 */
public abstract class Message {

    // Error message to use in OperationDeniedException
    protected static final String DENIED_USER_GROUP =
            "This operation is disabled in your user group.";

    // instance variables

    private LocalDate date;
    private User sender;
    protected String contents;

    /**
     * This is the main constructor for the Message class that initializes the
     * sender and date attributes
     * @param sender the sender of the message
     * @throws IllegalArgumentException if the sender is null
     */
    public Message(User sender) {
        if(sender == null){
            throw new IllegalArgumentException();
        }

        this.date = LocalDate.now();
        this.sender = sender;
    }

    /**
     * This is a "dummy" default constructor that initializes all attributes
     * to null, compiler gives error without it
     */
    Message(){
        this.date = null;
        this.sender = null;
        this.contents = null;
    }

    /**
     * This method simply returns the date attribute
     * @return LocalDate that holds the date
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * This method simply returns the sender attribute
     * @return a User, that is the sender of the message
     */
    public User getSender() {
       return this.sender;
    }

    /**
     * This is an abstract method that is to be implemented based on
     * the subclass
     * @return a formated String that contains attributed of subclasses
     */
    public abstract String getContents();

}