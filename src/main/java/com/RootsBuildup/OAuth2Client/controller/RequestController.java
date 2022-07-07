package com.RootsBuildup.OAuth2Client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
	public class RequestController {
		@GetMapping("/hello")
		public Object getMessage() {
			Map<String,String> obj = new HashMap<>();
			obj.put("key","value");
			return obj;
		}
	}
