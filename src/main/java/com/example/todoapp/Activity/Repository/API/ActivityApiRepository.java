package com.example.todoapp.Activity.Repository.API;

import com.example.todoapp.Activity.DTOs.ActivityDTO;
import com.example.todoapp.Activity.Models.Activity;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class ActivityApiRepository {
    RestTemplate restTemplate = new RestTemplateBuilder()
            .rootUri("https://l23ezadku1.execute-api.us-east-2.amazonaws.com/staging/todo")
            .defaultHeader("x-api-key"," n0KRcjVvm63H2Y7yubJJH5xZGentrS0k6qMw4lvB")
            .build();

    public ResponseEntity<Activity> getActivity(String id){
        return restTemplate.getForEntity("/{id}",Activity.class,id);
    }

    public ResponseEntity<List<Activity>> getAllActivity(String id){
        ResponseEntity<ActivityDTO[]> response = restTemplate.getForEntity("/", ActivityDTO[].class);
        List<Activity> activityList = Arrays.stream(Objects.requireNonNull(response.getBody()))
                .filter(act -> "kaue".equals(act.getLocalOwner()) && id.equals(act.getUserId()))
                .map(x -> x.toActivity())
                .collect(Collectors.toList());
        activityList.forEach(x -> System.out.println(x.getId()));

        return new ResponseEntity<List<Activity>>(activityList,response.getStatusCode());
    }

    public ResponseEntity<Activity> saveActivity(ActivityDTO activity){
        activity.setUpdatedAt(LocalDateTime.now().toString());
        ResponseEntity<ActivityDTO> response = restTemplate.exchange("/", HttpMethod.POST,
                new HttpEntity<>(activity, createJSONHeader()),
                ActivityDTO.class);
        return new ResponseEntity<Activity>(response.getBody().toActivity(),response.getStatusCode());
    }

    public void removeActivity(String id){
        restTemplate.delete("/"+id);
    }

    public void putActivity(Activity activity){
        ActivityDTO dto = new ActivityDTO();
        dto.toDTO(activity);
        ResponseEntity<ActivityDTO> response = restTemplate.exchange("/",HttpMethod.PUT,new HttpEntity<>(dto, createJSONHeader()),
                ActivityDTO.class);
    }

    private static HttpHeaders createJSONHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
