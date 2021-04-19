package org.txor.acme.paymentsystem.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.txor.acme.paymentsystem.domain.DispatchService;
import org.txor.acme.paymentsystem.domain.Payment;

@Component
public class KafkaConsumer {

    @Autowired
    private DispatchService dispatchService;

    @KafkaListener(topics = "${payment-dispatcher.topic}")
    public void receive(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
        ObjectMapper om = new ObjectMapper();
        try {
            Payment payment = om.readValue(consumerRecord.value(), Payment.class);
            dispatchService.dispatch(payment);
            acknowledgment.acknowledge();
        } catch (JsonProcessingException ignored) {
        }
    }

}
