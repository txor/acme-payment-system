package org.txor.acme.paymentsystem.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import org.txor.acme.paymentsystem.domain.InvalidPaymentException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {InvalidPaymentException.class, HttpServerErrorException.class, UnknownHttpStatusCodeException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public String otherErrorsHandler(RestClientException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String clientErrorHandler(RestClientException ex) {
        return ex.getMessage();
    }

}
