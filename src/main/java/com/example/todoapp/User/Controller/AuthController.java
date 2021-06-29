package com.example.todoapp.User.Controller;

import com.example.todoapp.Secutiry.TokenAuthenticationService;
import com.example.todoapp.User.DTO.LoginDTO;
import com.example.todoapp.User.Models.User;
import com.example.todoapp.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(path = "login")
public class AuthController {

    private final UserService service;

    @Autowired
    public AuthController(UserService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity UserLogin(@RequestBody LoginDTO login){
        try{
            User user = service.loadUserByUsername(login.getUsername());
            if(login.getPassword().equals(user.getPassword())){
                String token = TokenAuthenticationService.addAuthentication(user);
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.set("x-auth-token", token);
                return ResponseEntity.ok().headers(responseHeaders).body(user);
            }
            else{
                Map<Object, Object> model = new HashMap<>();
                model.put("error", "Username or password incorrect or not found");
                return ResponseEntity.badRequest().body(model);
            }
        }
        catch (Exception e){
            Map<Object, Object> model = new HashMap<>();
            model.put("error", e.getMessage());
            return internalServerError().body(model);
        }
    }
}
