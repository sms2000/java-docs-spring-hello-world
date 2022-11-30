package com.example.demo;

import java.util.*;
import java.net.http.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {
	private static long m_counter = 0;
	private static boolean m_posting = false;
	private static final String m_targetUrl = "http://www.example.com";

	public static void main(String[] args) {
		Timer t = new Timer();
		TimerTask tt = new TimerTask() {
			@Override
			public void run() {
				everyPeriodDo();
			};
		};

		t.schedule(tt, 0, 60 * 1000);

		SpringApplication.run(DemoApplication.class, args);
	}

	private static void everyPeriodDo() {
		m_counter++;

		if (!m_posting) {
			m_posting = true;

			Thread newThread = new Thread(() -> {
				posting();
			});

			newThread.start();
		}
	}

	private static void posting() {
		String body = "{\"Counter\" : \"" + m_counter + "\"}";

		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(m_targetUrl);
			httpPost.setHeader("Content-type", "application/json");

			StringEntity stringEntity = new StringEntity(body);
			httpPost.getRequestLine();
			httpPost.setEntity(stringEntity);

			httpClient.execute(httpPost);

			System.out.println("Request executed");
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}

		m_posting = false;
	}

	@RequestMapping("/")
	String answerNo() {
		return "Counter: " + m_counter;
	}
}
