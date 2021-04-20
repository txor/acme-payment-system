package org.txor.acme.paymentsystem.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.txor.acme.paymentsystem.domain.Payment;
import org.txor.acme.paymentsystem.domain.UpdateService;

import javax.validation.Valid;

@RestController
public class UpdateController {

    @Autowired
    private UpdateService updateService;

    @PostMapping("/update")
    public void update(@Valid @RequestBody Payment payment) {
        updateService.update(payment);
    }
}
