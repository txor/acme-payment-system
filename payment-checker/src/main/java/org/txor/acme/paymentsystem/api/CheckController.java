package org.txor.acme.paymentsystem.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.txor.acme.paymentsystem.domain.CheckService;
import org.txor.acme.paymentsystem.domain.Payment;

import javax.validation.Valid;

@RestController
public class CheckController {

    @Autowired
    private CheckService checkService;

    @PostMapping("/update")
    public void check(@Valid @RequestBody Payment payment) {
        checkService.check(payment);
    }
}
