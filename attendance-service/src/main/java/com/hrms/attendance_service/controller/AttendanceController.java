package com.hrms.attendance_service.controller;

import com.hrms.attendance_service.entity.Attendance;
import com.hrms.attendance_service.service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {


    private final AttendanceService service;

    public AttendanceController(AttendanceService service) {
        this.service = service;
    }

    @PostMapping("/check-in")
    public Attendance checkIn(@RequestHeader("X-Employee-Id") Long employeeId) {
        return service.checkIn(employeeId);
    }

    @PostMapping("/check-out")
    public Attendance checkOut(@RequestHeader("X-Employee-Id") Long employeeId) {
        return service.checkOut(employeeId);
    }

    @GetMapping("/today")
    public Attendance today(@RequestHeader("X-Employee-Id") Long employeeId) {
        return service.getToday(employeeId);
    }

    @GetMapping("/history")
    public List<Attendance> history(@RequestHeader("X-Employee-Id") Long employeeId) {
        return service.history(employeeId);
    }
}
