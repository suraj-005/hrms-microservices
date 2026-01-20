package com.hrms.attendance_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {
    private Long id;
    private String name;
    private boolean active;
}
