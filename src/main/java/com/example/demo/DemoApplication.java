package com.example.demo;

import java.util.*;
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

	private static String m_error;

	private static final String[] m_targetUrl = {
			"http://ogp1967.ogp.cloudns.ph:8764", // DATASAFE SERVER 8764
			"http://46.117.183.222:8764", // DATASAFE SERVER 8764
			"http://46.117.183.222:8765",
			"http://10.100.102.159:8765",
			"http://127.0.0.1:8765"
	};

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

		for (String url : m_targetUrl) {
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				httpPost.setHeader("Content-type", "application/json");

				StringEntity stringEntity = new StringEntity(body);
				httpPost.getRequestLine();
				httpPost.setEntity(stringEntity);

				httpClient.execute(httpPost);

				System.out.println("Request succeeded for " + url);
				m_error = null;
				break;
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				m_error = "Error: " + e.getMessage();
			}
		}

		m_posting = false;
	}

	@RequestMapping("/")
	String answerRoot() {
		if (m_error != null) {
			return m_error;
		}

		return "Counter: " + m_counter;
	}
}
