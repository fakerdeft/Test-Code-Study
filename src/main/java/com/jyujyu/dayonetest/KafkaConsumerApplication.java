package com.jyujyu.dayonetest;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;

import com.jyujyu.dayonetest.service.KafkaConsumerService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerApplication {

	private final KafkaConsumerService kafkaConsumerService;

	@Bean
	public NewTopic topic() {
		return TopicBuilder.name("test-topic")
			.build();
	}

	@KafkaListener(id = "test-id", topics = "test-topic")
	public void listen(String message) {
		kafkaConsumerService.process(message);
	}
}
