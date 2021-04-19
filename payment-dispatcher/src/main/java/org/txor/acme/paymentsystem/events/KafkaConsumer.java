package org.txor.acme.paymentsystem.events;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.txor.acme.paymentsystem.domain.DispatchService;

@Component
public class KafkaConsumer {

    @Autowired
    private DispatchService dispatchService;

    @KafkaListener(topics = "${payment-dispatcher.topic}")
    public void receive(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
        dispatchService.dispatch(consumerRecord.value());
        acknowledgment.acknowledge();
    }

}
