/*
  Name: Kevin Morales-Nguyen
  PID:  A17186624
 */


/**
 * This is a subclass of the abstract message class, it has extra attributes
 * to reflect a concrete implementation.
 * @author Kevin Morales-Nguyen
 * @since  10/18/21
 */
public class CodeMessage extends Message {
    private static final int MINIMUM_LINES = 10;

    // Error message to use in OperationDeniedException
    private static final String INVALID_INPUT =
            "The source path is not valid.";

    // Error message to use in OperationDeniedException for the invalid line number
    private static final String INVALID_CODE =
            "The files are not long enough.";

    // input validation criteria
    private static final String[] ACCEPTABLE_EXTENSIONS =
            new String[] {"html", "java", "py", "mjs", "ipynb", "md", "yml"};

    // instance variable
    private String extension;
    private int lines;

    /**
     * This is the main constructor for creating CodeMessage objects.
     * @param sender the sender of the message, a User
     * @param codeSource the file path of the code, a String
     * @param lines the number of lines of code
     * @throws OperationDeniedException lines of code smaller than 10 or extension is no valid
     * @throws IllegalArgumentException if sender or codeSource is null
     */
    public CodeMessage(User sender, String codeSource, int lines)
                        throws OperationDeniedException {
        super(sender);

        String temp_extension = extract_extension(codeSource);

        if(temp_extension == null){
            throw new OperationDeniedException(INVALID_INPUT);
        }

        if(lines < MINIMUM_LINES){
            throw new OperationDeniedException(INVALID_CODE);
        }

        if(sender == null || codeSource == null){
            throw new IllegalArgumentException();
        }


        this.contents = codeSource;
        this.extension = temp_extension;
        this.lines = lines;

    }

    /**
     * The method returns a formated string containig the relevant information of the object
     * @return a String with the object information
     */
    public String getContents() {
        String full_contents = "";
        full_contents = full_contents.concat(this.getSender().displayName() + " [");
        full_contents = full_contents.concat(this.getDate().toString() + "]: Code at ");
        full_contents = full_contents.concat(this.contents);

        return full_contents;
    }

    /**
     * This method returns the extention attribute specific to CodeMessage objects
     * @return A String that hold the attribute value of extension
     */
    public String getExtension() {
        return this.extension;
    }

    /**
     * This method returns the lines attribute
     * @return an integer the represents the number of lines in the CodeMessage
     */
    public int getLines() {
       return this.lines;
    }


    /**
     * This is a private helper method that helps extract the extension and also checks if
     * it is valid, this is called in the constructor so there is less code in constructor
     * @param codeSource_copy the String that contains the possible file path
     * @return a string that represents a vaild extension or null indicating invalid
     */
    private String extract_extension(String codeSource_copy) {
        String[] split_codeSource = codeSource_copy.split("\\.");
        String extension = null;

        if (split_codeSource.length == 1) {
            return null;
        } else {
            extension = split_codeSource[split_codeSource.length - 1].toLowerCase();
        }

        boolean verified = verify_extension(extension);

        if(verified == false){
            return null;
        }
        else{
            return extension;
        }
    }

    /**
     * This method loops through all possible extensions and returns boolean
     * that tells if it is valid or not
     * @param extension the file extension extracted from the previous helper method
     * @return returns true if the extension is valid, false otherwise
     */
    private boolean verify_extension(String extension){
        boolean valid = false;
        for(int i = 0; i < ACCEPTABLE_EXTENSIONS.length;i++){
            if(extension.equals(ACCEPTABLE_EXTENSIONS[i])){
                valid = true;
            }
        }
        return valid;
    }

}
