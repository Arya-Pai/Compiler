package com.project.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class CodeController {
	protected String[] secretKey() {
		String[] keys=new String[2];
		keys[0]="b8c8a6bc34a01222095f10aed2d2375";
		keys[1]="6dc4a16f6e1691c3b02299c2d73e96a9ac7eebded875cb6b3824ea79bfdedbe2";
		return keys;
		
	}
    @PostMapping("/compile")
    public ResponseEntity<String> compileCode(@RequestParam String code, @RequestParam String languageSelect ,@RequestParam(required = false) String input) {
        RestTemplate restTemplate = new RestTemplate();
        String[] keys=secretKey();
        String url = "https://api.jdoodle.com/v1/execute";
        System.out.println("Code: " + code);
        System.out.println("Language: " + languageSelect);
        
        Map<String, Object> request = new HashMap<>();
        request.put("clientId", keys[0]);
        request.put("clientSecret", keys[1]);
        request.put("script", code);
        request.put("language", languageSelect);
        request.put("stdin", input == null ? "" : input);
        Map<String, String> versionMap = Map.of(
        		  "python3", "3",
        		  "java", "5",
        		  "cpp", "0",
        		  "c", "0",
        		  "nodejs", "3"
        		);
        String versionIndex = versionMap.getOrDefault(languageSelect, "0");
        request.put("versionIndex", versionIndex);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        String responseBody=response.getBody();
        String output="No output found";
        
        if(responseBody!=null && responseBody.contains("\"output\"")){
        	int start=responseBody.indexOf("\"output\"")+10;
        	int end=responseBody.indexOf("\",", start);
        	if(end==-1) {
        		 end = responseBody.indexOf("\"}", start);
        	}
        	output=responseBody.substring(start,end).replace("\\n","\n").replace("\\t", "\t");
        }
        System.out.println("Output from Compiler:\n" + output);
        return ResponseEntity.ok(response.getBody());
    }
}
