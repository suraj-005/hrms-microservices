package com.hrms.employee_service.service;

import com.hrms.employee_service.dto.EmployeeDto;
import com.hrms.employee_service.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee create(EmployeeDto employeeDto);
    Employee getById(Long id);
    List<Employee> getAll();
    Employee update(Long id,Employee employee);
    void delete(Long id);
}
