import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by EddyJ on 7/12/16.
 */
public class Main {

    public static final String SESSION_USERNAME = "userName";
    public static final String SESSION_PASSWORD = "userPassword";
    static Map<String, User> users = new HashMap<>();


    public static void main(String[] args) {
        Spark.init();


        Spark.get("/", (request, response) -> {
            HashMap m = new HashMap();
            Session session = request.session();
            String name = session.attribute(SESSION_USERNAME);
            String password = session.attribute(SESSION_PASSWORD);
            User user = users.get(name);

            ModelAndView rval;
            if (user == null || user.getPassword().equals(password) || user.getPassword().isEmpty()){
                rval = new ModelAndView(m, "index.html");
            }
            else {
                m.put("user", user);
                rval = new ModelAndView(m, "messages.html");
            }
            return rval;

        }, new MustacheTemplateEngine());

        Spark.post("/create-user", (request, response) -> {
            User user = null;
            String name = request.queryParams("loginName");
            String password = request.queryParams("loginPassword");

                user = new User(name, password);
                users.put(name, user);

            Session session = request.session();
            session.attribute(SESSION_USERNAME, name);

            response.redirect("/");
            return "";
        });

        Spark.post("/create-message", (request, response) -> {
            HashMap m = new HashMap();
            Session session = request.session();
            String name = session.attribute(SESSION_USERNAME);
            User user = users.get(name);

            String message = request.queryParams("message");
            Messages newMessage = new Messages(message);
            user.addMessage(newMessage);
            response.redirect("/");
            return "";

        });

        Spark.post("/logout", (request, response) -> {
            request.session().invalidate();
            response.redirect("/");
            return "";
        });

        Spark.post("/delete-message", (request, response) -> {
            Session session = request.session();
            String name = session.attribute(SESSION_USERNAME);
            User user = users.get(name);

            int messageNumber = Integer.valueOf(request.queryParams("deletedMessage"));
            user.deleteMessage(messageNumber);
            response.redirect("/");
            return "";
        });

        Spark.post("/edit-message", (request, response) -> {
            Session session = request.session();
            String name = session.attribute(SESSION_USERNAME);
            User user = users.get(name);

            int messageNumber = Integer.valueOf(request.queryParams("messageNumber"));
            String editedMessage = request.queryParams("editMessage");
            Messages newMessage = new Messages(editedMessage);
            user.editMessage(messageNumber, newMessage);
            response.redirect("/");
            return "";
        });

    }
}

