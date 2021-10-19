/*
  Name: Kevin Morales-Nguyen
  PID:  A17186624
 */

/**
 * This class is a more specific subclass derived from the Message parent class
 * @author Kevin Morales-Nguyen
 * @since  10/18/21
 */
public class TextMessage extends Message {

    // Error message to use in OperationDeniedException
    private static final String EXCEED_MAX_LENGTH =
            "Your input exceeded the maximum length limit.";

    // input validation criteria
    private static final int MAX_TEXT_LENGTH = 500;

    /**
     *This is the main constructor for instantiating TextMessages
     * @param sender the sender of the message
     * @param text the content of the message
     * @throws OperationDeniedException If the length of text exceeds
     * the maximum length limit (500), you should throw an OperationDeniedException
     * with message EXCEED_MAX_LENGTH. if the sender or text is null, you should
     * throw an IllegalArgumentException.
     * @throws IllegalArgumentException sender or text is null
     */
    public TextMessage(User sender, String text)
            throws OperationDeniedException{
        super(sender);

        if(text.length() > MAX_TEXT_LENGTH){
            throw new OperationDeniedException(EXCEED_MAX_LENGTH);
        }

        if(text == null || sender == null){
            throw new IllegalArgumentException();
        }

        this.contents = text;
    }

    /**
     * This method returns a formated string with the TextMessages attributes
     * @return a String with attribute values
     */
    public String getContents() {
        String full_contents = "";
        full_contents = full_contents.concat(this.getSender().displayName() + " [");
        full_contents = full_contents.concat(this.getDate().toString() + "]: ");
        full_contents = full_contents.concat(this.contents);

        return full_contents;
    }
}
