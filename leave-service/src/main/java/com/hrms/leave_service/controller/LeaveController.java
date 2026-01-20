package com.hrms.leave_service.controller;

import com.hrms.leave_service.entity.Leave;
import com.hrms.leave_service.service.LeaveService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaves")
public class LeaveController {

    private final LeaveService service;

    public LeaveController(LeaveService service) {
        this.service = service;
    }

    // EMPLOYEE
    @PostMapping
    public Leave applyLeave(
            @RequestHeader("X-Employee-Id") Long employeeId,
            @RequestBody Leave leave) {

        return service.applyLeave(employeeId, leave);
    }

    @GetMapping("/me")
    public List<Leave> myLeaves(
            @RequestHeader("X-Employee-Id") Long employeeId) {

        return service.myLeaves(employeeId);
    }

    // HR / MANAGER
    @GetMapping("/pending")
    public List<Leave> pendingLeaves() {
        return service.pendingLeaves();
    }

    @PutMapping("/{id}/approve")
    public Leave approve(@PathVariable Long id) {
        return service.approveLeave(id);
    }

    @PutMapping("/{id}/reject")
    public Leave reject(@PathVariable Long id) {
        return service.rejectLeave(id);
    }
}
