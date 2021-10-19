/*
  Name: Kevin Morales-Nguyen
  PID:  A17186624
 */
import java.util.ArrayList;

/**
 * This is a more specfic class derived from the User abstract class
 * @author Kevin Morales-Nguyen
 * @since  10/18/21
 */
public class Tutor extends User {

    // instance variable
    private String customTitle;

    /**
     * This is the main construtor for the Tutor class, mostly ustelizes super
     * to construct objects
     * @param username username of the tutor
     * @param bio a small bio of the user
     */
    public Tutor(String username, String bio) {
        super(username, bio);
        customTitle = "Tutor";
    }

    /**
     * This method is used to gather the lastest messages from a message exchange
     * it grabs the log from the exhange and then appends it to a single string
     * approrpiatly based on the message type and whether is is a tutor or
     * student making the request
     *
     * @param me the message exchange to fetch the messaged from
     * @return a String that appends messages together from the
     * message exchange log
     * @throws IllegalArgumentException if the messageexhange is null or the user is not in the room
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
            aggregate_messages = aggregate_messages.concat(last_messages.get(i)
                    .getContents() + "\n");
        }

        return aggregate_messages;
    }

    /**
     * This method returns a formated string that prints out the tutors username with
     * custom title prepended
     * @return A String that contains instance attributes
     */
    public String displayName() {
        String title_string = "<" + this.customTitle + "> " + this.username;
        return title_string;
    }

    /**
     * This method sets the customTitle attribute
     * @param newTitle the new value of the customTitle attribute
     */
    public void setCustomTitle(String newTitle) {
        this.customTitle = newTitle;
    }

    /**
     *This method is used to create a new Autograder and add the Users from the list passed
     * as a argument, returns reference to new Autograder object
     * @param users a arraylist of users to be added to the newly created Autograder object
     * @return a reference to the new Autograder object that has the users from the
     * arraylist added
     * @throws IllegalArgumentException if users is null
     */
    public MessageExchange createAutograder(ArrayList<User> users) {
        if(users == null){
            throw new IllegalArgumentException();
        }

        Autograder new_autograder = new Autograder(this);

        for(int i = 0; i < users.size();i++){
            try {
                users.get(i).joinRoom(new_autograder);
            }
            catch(OperationDeniedException exception){
                System.out.println(exception.getMessage());
            }
        }

        return new_autograder;
    }

}
