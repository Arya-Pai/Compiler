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
	public ResponseEntity<Map<String, String>> transpileCode(@RequestParam String code,@RequestParam("languageSelect") String source,@RequestParam("targetLanguageSelect") String target){
		String prompt = "Convert the following code written in " + source +
                " to " + target + ". Only return the converted code. Do not include any explanation, comments, or additional text and convert the code according to the language and it need to be same.";

		String system_prompt = "You are a strict code transpiler. Return only valid working code in the target language with correct syntax. Do not include explanations or extra comments.REMEMBER YOU ARE A TRANSPILER WHICH CONVERTS CODE FROM ONE LANGUAGE TO ANOTHER WHICH IS MENTIONED . DO NOT GIVE ANY EXPLANATIONS OR ANYTHING JUST CONVERT THE CODE";

		
		
		
		RestTemplate rest=new RestTemplate();
		String url="https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key="+api_key;
		HttpHeaders header=new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		 header.set("x-goog-api-key", api_key);

		System.out.println(code);
		System.out.println(source+" "+target);
		
		Map<String, Object> message = new HashMap<>();
		message.put("text", system_prompt + "\n\n" + prompt + "\n\n" + code);

		Map<String, Object> content1 = new HashMap<>();
		content1.put("parts", List.of(message));

		Map<String, Object> generationConfig = new HashMap<>();
		generationConfig.put("temperature", 0.2);
		generationConfig.put("topK", 1);
		generationConfig.put("topP", 1.0);
		generationConfig.put("maxOutputTokens", 1024);

		Map<String, Object> body = new HashMap<>();
		body.put("contents", List.of(content1));
		body.put("generationConfig", generationConfig);
	
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, header);
		try {
			ResponseEntity<Map> response=rest.postForEntity(url, entity, Map.class);
			if (response.getStatusCode() == HttpStatus.OK) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
                Map<String, Object> firstCandidate = candidates.get(0);
                Map<String, Object> content = (Map<String, Object>) firstCandidate.get("content");
                List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                String transpiledCode = (String) parts.get(0).get("text");
                transpiledCode = transpiledCode
                        .replaceAll("(?i)```[a-z]*", "")  // remove ```python, ```java etc.
                        .replace("```", "")               // remove closing ```
                        .trim();
               
                Map<String, String> result = new HashMap<>();
                result.put("output", transpiledCode);
   			 return ResponseEntity.ok(result);
            }else {
            	return null;
            }
			
			 
		}catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("output", "Error compiling code: " + e.getMessage());
            return ResponseEntity.ok(error);
		}
	
		
		
	}
}
