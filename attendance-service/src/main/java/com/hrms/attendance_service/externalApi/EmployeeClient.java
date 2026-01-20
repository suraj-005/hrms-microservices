package com.hrms.attendance_service.externalApi;

import com.hrms.attendance_service.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="EMPLOYEE-SERVICE")
public interface EmployeeClient {

    @GetMapping("/employees/{id}")
    public EmployeeDto getEmployeeById(@PathVariable("id") Long id);
}
