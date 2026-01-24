package com.hrms.attendance_service.service;

import com.hrms.attendance_service.dto.LeaveApprovedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LeaveEventConsumer {

    private final AttendanceService attendanceService;

    public LeaveEventConsumer(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @KafkaListener(topics = "leave-approved",groupId = "attendance-group")
    public void consumeLeaveApprovedEvent(LeaveApprovedEvent event){
        attendanceService.markLeaveDays(event.getEmployeeId(),event.getStartDate(),event.getEndDate());
    }
}
