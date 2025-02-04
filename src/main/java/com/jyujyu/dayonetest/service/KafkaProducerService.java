package com.jyujyu.dayonetest.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

	private final KafkaTemplate<String, String> kafkaTemplate;

	public void send(String topic, String message) {
		kafkaTemplate.send(topic, message);
	}
}
