package org.txor.acme.paymentsystem.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.txor.acme.paymentsystem.domain.CheckService;
import org.txor.acme.paymentsystem.domain.Payment;

@RestController
public class CheckController {

    @Autowired
    private CheckService checkService;

    @PostMapping("/update")
    public void check(@RequestBody Payment payment) {
        checkService.check(payment);
    }
}
