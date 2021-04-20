package org.txor.acme.paymentsystem.events;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.support.Acknowledgment;
import org.txor.acme.paymentsystem.domain.DispatchService;
import org.txor.acme.paymentsystem.domain.InvalidPaymentException;
import org.txor.acme.paymentsystem.domain.Payment;
import org.txor.acme.paymentsystem.domain.PaymentConverter;
import org.txor.acme.paymentsystem.domain.UpdateStatus;
import org.txor.acme.paymentsystem.tools.TestMother;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class KafkaConsumerTest {

    @Test
    public void onMessageArrivalShouldConvertTheMessageThenCallDispatchServiceAndExecuteAckIfSuccess() throws InvalidPaymentException {
        Acknowledgment acknowledgment = mock(Acknowledgment.class);
        DispatchService dispatcherService = mock(DispatchService.class);
        when(dispatcherService.dispatch(any(Payment.class))).thenReturn(UpdateStatus.Ok);
        PaymentConverter paymentConverter = mock(PaymentConverter.class);
        when(paymentConverter.convert(anyString())).thenReturn(TestMother.createPayment());
        ConsumerRecord consumerRecord = new ConsumerRecord("topic", 1, 1, "dfs", "fdf");
        KafkaConsumer kafkaConsumer = new KafkaConsumer(dispatcherService, paymentConverter);

        kafkaConsumer.receive(consumerRecord, acknowledgment);

        verify(paymentConverter).convert(anyString());
        verify(dispatcherService).dispatch(any(Payment.class));
        verify(acknowledgment).acknowledge();
    }

    @Test
    public void onMessageArrivalShouldConvertTheMessageThenCallDispatchServiceAndDoNotExecuteAckIfFailure() throws InvalidPaymentException {
        Acknowledgment acknowledgment = mock(Acknowledgment.class);
        DispatchService dispatcherService = mock(DispatchService.class);
        when(dispatcherService.dispatch(any(Payment.class))).thenReturn(UpdateStatus.Ko);
        PaymentConverter paymentConverter = mock(PaymentConverter.class);
        ConsumerRecord consumerRecord = new ConsumerRecord("topic", 1, 1, "dfs", "fdf");
        KafkaConsumer kafkaConsumer = new KafkaConsumer(dispatcherService, paymentConverter);

        kafkaConsumer.receive(consumerRecord, acknowledgment);

        verifyNoInteractions(acknowledgment);
    }

    @Test
    public void onMessageArrivalShouldConvertTheMessageAndAndExecuteAckIfConvertFails() throws InvalidPaymentException {
        Acknowledgment acknowledgment = mock(Acknowledgment.class);
        DispatchService dispatcherService = mock(DispatchService.class);
        PaymentConverter paymentConverter = mock(PaymentConverter.class);
        when(paymentConverter.convert(anyString())).thenThrow(new InvalidPaymentException(new Exception()));
        ConsumerRecord consumerRecord = new ConsumerRecord("topic", 1, 1, "dfs", "fdf");
        KafkaConsumer kafkaConsumer = new KafkaConsumer(dispatcherService, paymentConverter);

        kafkaConsumer.receive(consumerRecord, acknowledgment);

        verify(acknowledgment).acknowledge();
    }

}
