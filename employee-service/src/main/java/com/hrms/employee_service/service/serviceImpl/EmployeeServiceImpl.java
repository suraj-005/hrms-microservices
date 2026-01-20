package com.hrms.employee_service.service.serviceImpl;

import com.hrms.employee_service.dto.EmployeeDto;
import com.hrms.employee_service.entity.Employee;
import com.hrms.employee_service.repository.EmployeeRepository;
import com.hrms.employee_service.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee create(EmployeeDto employeeDto) {
        Employee employee=new Employee();
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setDepartment(employeeDto.getDepartment());
        employee.setSalary(employeeDto.getSalary());
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getById(Long id) {
        return employeeRepository.findById(id).orElseThrow(()->new RuntimeException("Employee not found"));
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee update(Long id, Employee employee) {
//        Employee existingEmployee=getById(id);
//        existingEmployee.setName(employee.getName());
        return null;
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}
