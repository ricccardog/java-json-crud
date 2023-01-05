package codes.wink.springboot.service.impl;

import lombok.AllArgsConstructor;
import codes.wink.springboot.entity.User;
import codes.wink.springboot.repository.UserRepository;
import codes.wink.springboot.service.UserService;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public User createUser(User user) {
        return UserRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) throws Exception {
        User user = UserRepository.findById(userId);
        return user;
    }

    @Override
    public JSONArray getAllUsers() {
        return UserRepository.findAll();
    }

    @Override
    public User updateUser(User user) throws Exception {
        User existingUser = UserRepository.findById(user.getId());
        if(existingUser.firstName != null) {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            User updatedUser = UserRepository.save(existingUser);
            return updatedUser;
        } else {
            throw new Exception("User does not exist");
        }
    }
   
    @Override
    public void deleteUser(Long userId) throws Exception {
        try {
            UserRepository.deleteById(userId);
        } catch(Exception e ) {
            throw new Exception("User does not exist");
        }
    } 
}