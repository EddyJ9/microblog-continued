import java.util.ArrayList;

/**
 * Created by EddyJ on 7/12/16.
 */
public class Messages extends ArrayList<Messages> {
    private String name;

    public Messages(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
