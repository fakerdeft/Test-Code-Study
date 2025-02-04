package com.jyujyu.dayonetest;

import com.jyujyu.dayonetest.service.KafkaConsumerService;
import com.jyujyu.dayonetest.service.KafkaProducerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@Order(0)
@DirtiesContext
class KafkaConsumerApplicationTests extends IntegrationTest {

  @Autowired private KafkaProducerService kafkaProducerService;

  @MockitoBean private KafkaConsumerService kafkaConsumerService;

  @Test
  void kafkaSendAndConsumeTest() {
    String topic = "test-topic";
    String expectValue = "expect-value";

    kafkaProducerService.send(topic, expectValue);

    var stringCaptor = ArgumentCaptor.forClass(String.class);
    Mockito.verify(kafkaConsumerService, Mockito.timeout(5000).times(1))
        .process(stringCaptor.capture());

    Assertions.assertEquals(expectValue, stringCaptor.getValue());
  }
}
