package com.hrms.leave_service.service;

import com.hrms.leave_service.dto.LeaveApprovedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LeaveEventProducer {
    private static final String TOPIC="leave-approved";

    private final KafkaTemplate<String, LeaveApprovedEvent> kafkaTemplate;

    public LeaveEventProducer(KafkaTemplate<String, LeaveApprovedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendLeaveApprovedEvent(LeaveApprovedEvent event){
        kafkaTemplate.send(TOPIC,event);
    }
}
