package org.txor.acme.paymentsystem.events;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.txor.acme.paymentsystem.domain.DispatchService;
import org.txor.acme.paymentsystem.domain.InvalidPaymentException;
import org.txor.acme.paymentsystem.domain.Payment;
import org.txor.acme.paymentsystem.domain.PaymentConverter;

@Component
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    private final DispatchService dispatchService;
    private final PaymentConverter paymentConverter;

    @Autowired
    public KafkaConsumer(DispatchService dispatchService, PaymentConverter paymentConverter) {
        this.dispatchService = dispatchService;
        this.paymentConverter = paymentConverter;
    }

    @KafkaListener(topics = "${payment-dispatcher.topic}")
    public void receive(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
        try {
            Payment payment = paymentConverter.convert(consumerRecord.value());
            dispatchService.dispatch(payment);
            acknowledgment.acknowledge();
        } catch (InvalidPaymentException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
    }

}
