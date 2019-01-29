package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

/**
 * ClassName:
 * Function:
 * date: 2019年01月15日
 *
 * @author 许嘉阳
 */
@RestController
public class UserController {

    @Autowired
    RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RequestMapping(value = "/userInfo", produces = {
            "application/json;charset=UTF-8"}, method = RequestMethod.POST)
    public String getResource(@RequestParam String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8082/api/profile", HttpMethod.GET, requestEntity, String.class);
        String result = response.getBody();
        return result;
    }

}
