package com.project.controller;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@RestController
@RequestMapping("/api")
public class CodeController {
	@Value("${joodle.api.key1}")
	private String jdoodle_api_key1;
	@Value("${joodle.api.key2}")
	private String jdoodle_api_key2;

	@PostMapping("/compile")
	public ResponseEntity<Map<String, String>> compileCode(@RequestParam String code,
			@RequestParam String languageSelect, @RequestParam(required = false) String input) {
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

		Map<String, String> versionMap = Map.of("python3", "3", "java", "5", "cpp", "0", "c", "0", "nodejs", "3");
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

	@PostMapping("/download-pdf")
	public ResponseEntity<byte[]> generatePdf(@RequestParam String code, @RequestParam String input,
			@RequestParam String output) {
		try {
			Document document = new Document();
			Font headingFont = new Font(Font.HELVETICA, 14, Font.BOLD);
			Font codeFont = new Font(Font.COURIER, 12);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();
			document.add(new Paragraph("Code\n", headingFont));
			document.add(new Paragraph( code, codeFont));
			if (input != null && !input.trim().isEmpty() && !"null".equalsIgnoreCase(input.trim())) {
	            document.add(new Paragraph("\nInput:\n", headingFont));
	            document.add(new Paragraph(input, codeFont));
	        }
			
			document.add(new Paragraph("Output:\n", headingFont));
			document.add(new Paragraph(output, codeFont));
			document.close();
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.APPLICATION_PDF);
			header.setContentDisposition(ContentDisposition.attachment().filename("result.pdf").build());
			return new ResponseEntity<>(baos.toByteArray(), header, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(("Error: " + e.getMessage()).getBytes());
			
		}

	}

}
