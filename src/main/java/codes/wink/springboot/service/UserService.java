package codes.wink.springboot.service;

import codes.wink.springboot.entity.User;

import org.json.simple.JSONArray;

public interface UserService {
    User createUser(User user);

    User getUserById(Long userId) throws Exception;

    JSONArray getAllUsers();

    User updateUser(User user) throws Exception;

    void deleteUser(Long userId) throws Exception;

}