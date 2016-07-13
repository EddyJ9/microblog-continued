import java.util.ArrayList;
import java.util.List;

/**
 * Created by EddyJ on 7/12/16.
 */
public class User {
    private String name;
    private String password;
    private List<Messages> messages = new ArrayList<>();


    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public List<Messages> getMessage() {
        return messages;
    }

    public String getPassword() {
        return password;
    }

    public void addMessage(Messages message) {
        this.messages.add(message);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void deleteMessage(int messageNumber){
        messages.remove(messageNumber);
    }

    public void editMessage(int messageNumber, Messages message){
        messages.remove(messageNumber).add(messageNumber, message);
    }
}