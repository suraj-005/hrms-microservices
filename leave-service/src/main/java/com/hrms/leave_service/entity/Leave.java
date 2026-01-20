package com.hrms.leave_service.entity;

import com.hrms.leave_service.utils.LeaveStatus;
import com.hrms.leave_service.utils.LeaveTypes;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name="leaves")
@Data
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;

    @Enumerated(EnumType.STRING)
    private LeaveTypes type;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    private LocalDate startDate;
    private LocalDate endDate;

    private String reason;

    private LocalDate appliedDate;

}
