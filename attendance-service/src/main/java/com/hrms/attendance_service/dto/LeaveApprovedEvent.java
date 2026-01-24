package com.hrms.attendance_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveApprovedEvent {
    private Long leaveId;
    private Long employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String leaveType;
}
