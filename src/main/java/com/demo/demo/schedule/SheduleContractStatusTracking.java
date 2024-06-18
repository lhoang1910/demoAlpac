package com.demo.demo.schedule;

import com.demo.demo.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SheduleContractStatusTracking {

    @Autowired
    ContractService contractService;

    @Scheduled(cron = "0 0 12 * * ?")
//    @Scheduled(cron = "0/5 * * * * ?")
    public void trackingContractStatus() {
        contractService.trackingContractStatus();
    }
}
