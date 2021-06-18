package com.example.todoapp.Activity.Repository.API;

import com.example.todoapp.Activity.Models.Activity;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ActivityApiRepository {
    RestTemplate restTemplate = new RestTemplateBuilder()
            .rootUri("https://l23ezadku1.execute-api.us-east-2.amazonaws.com/staging")
            .defaultHeader("x-api-key"," n0KRcjVvm63H2Y7yubJJH5xZGentrS0k6qMw4lvB")
            .build();

//    public ResponseEntity<Activity> getActivity(String id){
//        restTemplate.getForEntity("/{id}",)
//    }
}
