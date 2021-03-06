package com.example.todoapp.User.Controller;
import com.example.todoapp.User.DTO.DecodedTokenDTO;
import com.example.todoapp.User.DTO.UpdateDTO;
import com.example.todoapp.User.Models.User;
import com.example.todoapp.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "user")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        try {
            return new ResponseEntity<List<User>>(service.getUsers(),HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping
    public ResponseEntity<User> addUser(@Validated @RequestBody User user){
        try {
            return new ResponseEntity<User>(service.createUser(user),HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/")
    public ResponseEntity<User> updateUser(@RequestBody UpdateDTO user){
        DecodedTokenDTO info = (DecodedTokenDTO) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        try{
            return new ResponseEntity<User>(service.updateUser(user,info.getId()),HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable String id){
        try {
            return new ResponseEntity<User>(service.deleteUser(id),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
}
