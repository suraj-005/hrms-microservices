package com.hrms.attendance_service.service;

import com.hrms.attendance_service.entity.Attendance;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    Attendance checkIn(Long employeeId);

    Attendance checkOut(Long employeeId);

    Attendance getToday(Long employeeId);

    List<Attendance> history(Long employeeId);

    void markLeaveDays(Long empId, LocalDate startDate,LocalDate endDate);
}
