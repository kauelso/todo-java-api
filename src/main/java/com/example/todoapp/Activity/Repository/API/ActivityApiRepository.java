package com.example.todoapp.Activity.Repository.API;

import com.example.todoapp.Activity.DTOs.ActivityDTO;
import com.example.todoapp.Activity.Models.Activity;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class ActivityApiRepository {
    RestTemplate restTemplate = new RestTemplateBuilder()
            .rootUri("https://l23ezadku1.execute-api.us-east-2.amazonaws.com/staging/todo")
            .defaultHeader("x-api-key"," n0KRcjVvm63H2Y7yubJJH5xZGentrS0k6qMw4lvB")
            .build();

    public ResponseEntity<Activity> getActivity(String id){
        return restTemplate.getForEntity("/{id}",Activity.class,id);
    }
    public ResponseEntity<Activity> saveActivity(ActivityDTO activity){
        activity.setUpdatedAt(activity.getUpdatedAt());
        return restTemplate.exchange("/", HttpMethod.POST,
                new HttpEntity<>(activity,createJSONHeader()),
                Activity.class);
    }
    private static HttpHeaders createJSONHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
