package com.example.todoapp.User.Service;

import com.example.todoapp.User.Models.User;
import com.example.todoapp.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("user") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUser(Long id){
        return userRepository.findById(id).get();
    }

    public User createUser(User user) throws IllegalAccessException {
        Optional<User> response = userRepository.findByUsername(user.getUsername());
        if(response.isPresent()){
            throw new IllegalAccessException();
        }
        return userRepository.save(user);
    }

    public User updateUser(User user, Long id) throws IllegalAccessException {
        Optional<User> response = userRepository.findById(id);
        if(!response.isPresent()){
            throw new IllegalAccessException();
        }
        Optional<User> updatedActivity = response.map(usr -> {
            if(user.getUsername() != null)usr.setUsername(user.getUsername());
            if(user.getPassword() != null)usr.setPassword(user.getPassword());
            return usr;
        });

        return userRepository.save(updatedActivity.get());
    }

    public User deleteUser(Long id) throws IllegalAccessException {
        Optional<User> response = userRepository.findById(id);
        if(!response.isPresent()){
            throw new IllegalAccessException();
        }
        try {
            userRepository.deleteById(id);
            return response.get();
        }
        catch (Exception e){
            System.out.println(e.toString());
            throw e;
        }
    }
}
