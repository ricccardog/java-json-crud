package codes.wink.springboot.repository;

import codes.wink.springboot.entity.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@SuppressWarnings("unchecked")
public interface UserRepository {

    public static void main(String[] args) {
    }

    // READ FILE
    public static JSONArray manageFile() {
        JSONParser jsonParser = new JSONParser();
        JSONArray userList = new JSONArray();

        try (FileReader reader = new FileReader("users.json")) {
            JSONArray current = (JSONArray) jsonParser.parse(reader);
            userList.addAll(current);
        } catch (FileNotFoundException e) {
            try (FileWriter file = new FileWriter("users.json")) {
                file.write("");
                file.flush();
            } catch (IOException error) {
                error.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return userList;
    }

    // WRITE FILE
    private static void writeToFile(JSONArray userlist) {
        try (FileWriter file = new FileWriter("users.json")) {
            file.write(userlist.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // GET ALL
    public static JSONArray findAll() {
        JSONArray userList = manageFile();
        return userList;
    }
    
    // GET ONE
    public static User findById(Long id) throws Exception {
        JSONArray userList = manageFile();
        User target = new User();
        target.id = id;
        userList.forEach(u -> {
            User current = parseUserObject((JSONObject) u);
            if (current.id == id) {
                target.firstName = current.firstName;
                target.lastName = current.lastName;
                target.email = current.email;
            }
        });
        if (target.firstName != null) {
            return target;
        } else {
            throw new Exception("User not found");
        }
    }

    // POST PUT
    public static User save(User user) {
        // POST new user
        if (user.id == null) {
            JSONArray userList = manageFile();

            JSONObject incomingUser = new JSONObject();

            Long newIndex;
            if (userList.size() != 0) {
                JSONObject lastElement = (JSONObject) userList.get(userList.size() - 1);
                newIndex = (Long) lastElement.get("id") + 1;
            } else {
                newIndex = (long) 1;
            }
            incomingUser.put("id", newIndex);
            user.setId(newIndex);
            incomingUser.put("firstName", user.firstName);
            incomingUser.put("lastName", user.lastName);
            incomingUser.put("email", user.email);
            userList.add(incomingUser);
            writeToFile(userList);
            return user;
            // PUT EXISTING USER
        } else {
            JSONArray userList = manageFile();
            userList.forEach(u -> {
                JSONObject obj = (JSONObject) u;
                if (obj.get("id").equals(user.id)) {
                    obj.put("firstName", user.firstName);
                    obj.put("lastName", user.lastName);
                    obj.put("email", user.email);
                }
            });
            writeToFile(userList);
            return user;
        }
    }

    // DELETE
    public static void deleteById(Long id) throws Exception {
        JSONArray updatedList = new JSONArray();
        JSONArray userList = manageFile();
        userList.forEach(u -> {
            User current = parseUserObject((JSONObject) u);
            if (current.id != id) {
                updatedList.add(u);
            }
        });

        if (updatedList.size() == userList.size()) {
            throw new Exception("User does not exist");
        } else {
            writeToFile(updatedList);
        }

    }

    // Match user through Id
    private static User parseUserObject(JSONObject user) {
        User current = new User();
        Long id = (Long) user.get("id");
        current.id = id;
        String firstName = (String) user.get("firstName");
        current.firstName = firstName;
        String lastName = (String) user.get("lastName");
        current.lastName = lastName;
        String email = (String) user.get("email");
        current.email = email;
        return current;
    }

}