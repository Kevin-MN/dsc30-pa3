/*
  Name: Kevin Morales-Nguyen
  PID:  A17186624
 */

import java.time.LocalDate;

import com.sun.corba.se.spi.orb.Operation;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 * Messenger Application Test
 * @author Kevin Morales-Nguyen
 * @since  10/18/21
 */
public class MessengerApplicationTest {

    /*
      Messages defined in starter code. Remember to copy and paste these strings to the
      test file if you cannot directly access them. DO NOT change the original declaration
      to public.
     */
    private static final String INVALID_INPUT =
            "The source path is not valid.";

    // Error message to use in OperationDeniedException
    protected static final String JOIN_ROOM_FAILED =
            "Failed to join the chat room.";
    protected static final String INVALID_MSG_TYPE =
            "Cannot send this type of message to the specified room.";

    // Error message to use in OperationDeniedException
    protected static final String SESSION_ENDED =
            "Session has already ended. Ticket can't be resolved";
    protected static final String NO_ACCESS =
            "Only tutors can actively resolve tickets.";
    protected static final String NO_LOGS =
            "There are no more messages in the log.";


    private static final String FETCH_DENIED_MSG =
            "This message cannot be fetched because you are not a premium user.";


    /*
      Global test variables. Initialize them in @Before method.
     */
    Tutor marina;
    MessageExchange room;
    Student kevin;
    Student violet;
    ArrayList<User> users;
    Autograder room2;

    /*
      The date used in Message and its subclasses. You can directly
      call this in your test methods.
     */
    LocalDate date = LocalDate.now();

    /*
     * Setup
     */
    @Before
    public void setup() {
        marina = new Tutor("Marina", "Instructor");
        room2 = new Autograder(marina);
        room = new Autograder(marina);
        kevin = new Student("Kevin", "DSC major");
        violet = new Student("Violet", "Auto Memory Doll");
        users = new ArrayList<User>();
        users.add(kevin);
        users.add(violet);
    }


    @Test (expected = IllegalArgumentException.class)
    public void user_constructer_test1(){
        User test = new Student(null, "dsc30");

    }

    @Test (expected = IllegalArgumentException.class)
    public void user_constructer_test2(){
        User test = new Student("Kevin2", null);

    }


    @Test (expected = IllegalArgumentException.class)
    public void user_setBio_test1(){
       kevin.setBio(null);

    }

    @Test
    public void user_setBio_test2(){
        assertEquals("DSC major", kevin.displayBio());
        kevin.setBio("CS major");
        assertEquals("CS major", kevin.displayBio());
    }

    @Test
    public void user_displayBio_test1(){
        assertEquals("Auto Memory Doll", violet.displayBio());
    }

    @Test //(expected = OperationDeniedException.class)
    public void user_joinRoom_test1(){

        try {
            kevin.joinRoom(room);
            kevin.joinRoom(room);
        }
        catch(OperationDeniedException exception){
            assertEquals(JOIN_ROOM_FAILED, exception.getMessage());
        }



    }

    @Test
    public void user_joinRoom_test2(){
        try {
            kevin.joinRoom(room);


            assertEquals(false, room.addUser(kevin));
        }catch(OperationDeniedException exception){
            assertNotEquals(JOIN_ROOM_FAILED, exception.getMessage());
        }


    }

    @Test
    public void user_joinRoom_test3(){
        try {
            room2.stopSession();

            kevin.joinRoom(room2);
        }catch(OperationDeniedException exception){
            assertEquals(JOIN_ROOM_FAILED, exception.getMessage());
        }
    }


    @Test
    public void user_joinRoom_test4(){

        try {
            assertEquals(1, room.getUsers().size());
            kevin.joinRoom(room);
            assertEquals(2, room.getUsers().size());
        }catch(OperationDeniedException exception){

        }
    }

    @Test (expected = IllegalArgumentException.class)
    public void user_quitRoom_test1() {
        kevin.quitRoom(null);
    }

    @Test
    public void user_quitRoom_test2() {
        try {
            kevin.joinRoom(room2);
            assertEquals(2, room2.getUsers().size());
        }
        catch(OperationDeniedException exception){

        }
        kevin.quitRoom(room2);

        assertEquals(1, room2.getUsers().size());
    }


    @Test (expected = IllegalArgumentException.class)
    public void user_sendMessage_test1() {

        kevin.sendMessage(room2, " cool.py", -1);
        ArrayList<Message> return_logs = room2.getLog(kevin);
        assertEquals(1, return_logs.size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void user_sendMessage_test2() {

        kevin.sendMessage(null, " cool.py", -1);
        ArrayList<Message> return_logs = room2.getLog(kevin);
        assertEquals(1, return_logs.size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void user_sendMessage_test3() {

        kevin.sendMessage(room2, null, -1);
        ArrayList<Message> return_logs = room2.getLog(kevin);
        assertEquals(1, return_logs.size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void user_sendMessage_test4() {
        room2.addUser(kevin);
        kevin.sendMessage(room2, " cool.py", -1);
        ArrayList<Message> return_logs = room2.getLog(kevin);
        assertEquals(1, return_logs.size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void user_sendMessage_test5() {

        kevin.sendMessage(room2, " cool.py", 10);

        kevin.sendMessage(room2, " cool.htm", 10);

        ArrayList<Message> return_logs = room2.getLog(kevin);
        assertEquals(1, return_logs.size());
    }


    @Test
    public void user_resolveTicket_test1() {

        room2.stopSession();

        try{
            room2.resolveTicket(marina);
        }
        catch(OperationDeniedException exception){
            assertEquals(SESSION_ENDED, exception.getMessage());
        }

    }

    @Test
    public void user_resolveTicket_test2() {



        try{
            room2.resolveTicket(kevin);
        }
        catch(OperationDeniedException exception){
            assertEquals(NO_ACCESS, exception.getMessage());
        }

    }

    @Test
    public void user_resolveTicket_test3() {



        try{
            room2.resolveTicket(marina);
        }
        catch(OperationDeniedException exception){
            assertEquals(NO_LOGS, exception.getMessage());
        }

    }






    @Test
    public void user_addUser_test1() {
        room2.addUser(kevin);

        assertEquals(true, room2.getUsers().contains(kevin));
        assertEquals(2, room2.getUsers().size());

    }


















































    /*
      Recap: Assert exception without message
     */
    @Test (expected = IllegalArgumentException.class)
    public void testPremiumUserThrowsIAE() {
        marina = new Tutor("Marina", null);
    }

    /*
      Assert exception with message
     */
    @Test
    public void testPhotoMessageThrowsODE() {
        try {
            CodeMessage pm = new CodeMessage(marina, "PA02.zip", 10);
            fail("Exception not thrown"); // will execute if last line didn't throw exception
        } catch (OperationDeniedException ode) {
            assertEquals(INVALID_INPUT, ode.getMessage());
        }
    }

    /*
     * Assert message content without hardcoding the date
     */
    @Test
    public void testTextMessageGetContents() {
        try {
            TextMessage tm = new TextMessage(marina, "A sample text message.");

            // concatenating the current date when running the test
            String expected = "<Tutor> Marina [" + date + "]: A sample text message.";
            assertEquals(expected, tm.getContents());
        } catch (OperationDeniedException ode) {
            fail("ODE should not be thrown");
        }
    }

    /*
      TODO: Add your tests
     */
}
