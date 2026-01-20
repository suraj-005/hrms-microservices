package com.hrms.attendance_service.service;

import com.hrms.attendance_service.dto.EmployeeDto;
import com.hrms.attendance_service.entity.Attendance;
import com.hrms.attendance_service.entity.AttendanceStatus;
import com.hrms.attendance_service.externalApi.EmployeeClient;
import com.hrms.attendance_service.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository repository;
    private final EmployeeClient employeeClient;

    public AttendanceServiceImpl(AttendanceRepository repository, EmployeeClient employeeClient) {
        this.repository = repository;
        this.employeeClient = employeeClient;
    }

    @Override
    public Attendance checkIn(Long employeeId) {
        EmployeeDto employeeDto=employeeClient.getEmployeeById(employeeId);
        LocalDate today = LocalDate.now();

        if (repository.findByEmployeeIdAndDate(employeeId, today).isPresent()) {
            throw new RuntimeException("Already checked in today");
        }

        Attendance attendance=new Attendance();
        attendance.setEmployeeId(employeeId);
        attendance.setStatus(AttendanceStatus.PRESENT);
        attendance.setDate(today);
        attendance.setCheckInTime(LocalDateTime.now());

        return repository.save(attendance);
    }

    @Override
    public Attendance checkOut(Long employeeId) {
        Attendance attendance = repository
                .findByEmployeeIdAndDate(employeeId, LocalDate.now())
                .orElseThrow(() -> new RuntimeException("Check-in not found"));

        attendance.setCheckOutTime(LocalDateTime.now());
        return repository.save(attendance);
    }

    @Override
    public Attendance getToday(Long employeeId) {
        return repository
                .findByEmployeeIdAndDate(employeeId, LocalDate.now())
                .orElse(null);
    }

    @Override
    public List<Attendance> history(Long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }
}
