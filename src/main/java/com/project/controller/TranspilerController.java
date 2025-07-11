package com.project.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class TranspilerController {
	@Value("${gemini.api.key}")
	private String api_key;


	@PostMapping("/transpile")
	public ResponseEntity<String> transpileCode(@RequestParam String code,@RequestParam("languageSelect") String source,@RequestParam("targetLanguageSelect") String target){
		String prompt = "Convert the following code written in " + source +
                " to " + target + ". Only return the converted code. Do not include any explanation, comments, or additional text and convert the code according to the language and it need to be same.";

		String system_prompt = "You are a strict code transpiler. Return only valid working code in the target language with correct syntax. Do not include explanations or extra comments.";

		
		
		
		RestTemplate rest=new RestTemplate();
		String url="https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key="+api_key;
		HttpHeaders header=new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		 header.set("x-goog-api-key", api_key);

		System.out.println(code);
		System.out.println(source+" "+target);
		
		Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("parts", List.of(Map.of("text", system_prompt + "\n\n" + prompt)));
        
        Map<String, Object> body = Map.of("contents", List.of(message));
	
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, header);
		try {
			ResponseEntity<Map> response=rest.postForEntity(url, entity, Map.class);
			if (response.getStatusCode() == HttpStatus.OK) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
                Map<String, Object> firstCandidate = candidates.get(0);
                Map<String, Object> content = (Map<String, Object>) firstCandidate.get("content");
                List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                String transpiledCode = (String) parts.get(0).get("text");

                return ResponseEntity.ok(transpiledCode.trim());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error from Gemini API: " + response.getStatusCode());
            }
		}catch(Exception e) {
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                .body("Error during transpilation: " + e.getMessage());
		}
		
		
	}
}
