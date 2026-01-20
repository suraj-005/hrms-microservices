package com.hrms.employee_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class EmployeeDto {
    private Long id;

    @NotBlank
    private String name;

    @Email
    private String email;

    private String department;
    private Double salary;
}
