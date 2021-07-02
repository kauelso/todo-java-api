package com.example.todoapp.User.Controller;

import com.example.todoapp.ActivityApplication;
import com.example.todoapp.Secutiry.TokenAuthenticationService;
import com.example.todoapp.User.DTO.DecodedTokenDTO;
import com.example.todoapp.User.DTO.UpdateDTO;
import com.example.todoapp.User.Models.User;
import com.example.todoapp.User.Repository.UserRepository;
import com.example.todoapp.User.Service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.get;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(value = SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ActivityApplication.class
)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    private AutoCloseable autoCloseable;

    private User user;

    private User userAdmin;

    @MockBean
    private UserService service;

//    @MockBean
//    private UserRepository repository;

    @BeforeEach
    void SetUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        user = new User("1",
                "username",
                "password",
                false,
                false);
        userAdmin = new User("1",
                "username",
                "password",
                false,
                true);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void ListUsersTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void CreateUserTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"usernameTest\"," +
                        "\"password\": \"123456\"," +
                        "\"remoteDB\": \"false\"," +
                        "\"admin\": \"false\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void UpdateUserWithoutToken() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"username\"," +
                        "\"password\": \"123456\"}"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void UpdateUserWithToken() throws Exception {
        String token = TokenAuthenticationService.addAuthentication(user);
        mvc.perform(MockMvcRequestBuilders.put("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+token)
                .content("{\"username\": \"username\"," +
                        "\"password\": \"123456\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void RemoveUserWithoutToken() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"username\"," +
                        "\"password\": \"123456\"}"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void RemoveUserWithTokenNotAdmin() throws Exception {
        String token = TokenAuthenticationService.addAuthentication(user);
        mvc.perform(MockMvcRequestBuilders.delete("/user/abc")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+token)
                .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
    @Test
    void RemoveUserWithTokenAdmin() throws Exception {
        String token = TokenAuthenticationService.addAuthentication(userAdmin);
        mvc.perform(MockMvcRequestBuilders.delete("/user/abc")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+token)
                .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}