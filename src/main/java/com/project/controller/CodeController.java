package com.project.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class CodeController {
	@Value("${joodle.api.key1}")
	private String jdoodle_api_key1;
	@Value("${joodle.api.key2}")
	private String jdoodle_api_key2;
	
	
    @PostMapping("/compile")
    public ResponseEntity<Map<String, String>> compileCode(@RequestParam String code, @RequestParam String languageSelect ,@RequestParam(required = false) String input) {
        RestTemplate restTemplate = new RestTemplate();
 
        String url = "https://api.jdoodle.com/v1/execute";
        System.out.println("Code: " + code);
        System.out.println("Language: " + languageSelect);
        
        Map<String, Object> request = new HashMap<>();
        request.put("clientId", jdoodle_api_key1);
        request.put("clientSecret", jdoodle_api_key2);
        request.put("script", code);
        request.put("language", languageSelect);
        request.put("stdin", input == null ? "" : input);

        Map<String, String> versionMap = Map.of(
            "python3", "3", "java", "5", "cpp", "0", "c", "0", "nodejs", "3"
        );
        request.put("versionIndex", versionMap.getOrDefault(languageSelect, "0"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(response.getBody(), Map.class);
            String output = responseMap.get("output").toString();

            Map<String, String> result = new HashMap<>();
            result.put("output", output);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("output", "Error compiling code: " + e.getMessage());
            return ResponseEntity.ok(error);
        }
    }
    
    
}
