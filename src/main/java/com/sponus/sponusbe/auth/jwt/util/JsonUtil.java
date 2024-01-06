package com.sponus.sponusbe.auth.jwt.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

public class JsonUtil {

	public static Map<String, Object> getBody(HttpServletRequest request) throws IOException {
		return convertJsonToMap(
			readRequestBody(request)
		);
	}

	private static String readRequestBody(HttpServletRequest request) throws IOException {

		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));

		char[] charBuffer = new char[128];

		int bytesRead;
		while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
			stringBuilder.append(charBuffer, 0, bytesRead);
		}

		return stringBuilder.toString();
	}

	private static Map<String, Object> convertJsonToMap(String json) throws JsonProcessingException {
		return new ObjectMapper().readValue(json, new TypeReference<>() {
		});
	}
}
