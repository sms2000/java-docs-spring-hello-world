package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@RequestMapping("/moo/boo")
	String answerMooBoo() {
		return "!!! Hello World (/moo/boo) !!!! " + System.currentTimeMillis();
	}

	@RequestMapping("/moo")
	String answerMoo() {
		return "!!! Hello World (/moo) !!!! " + System.currentTimeMillis();
	}

	@RequestMapping("/")
	String answerNo() {
		return "!!! Hello World !!!! " + System.currentTimeMillis();
	}
}
