package com.hrms.leave_service.service;

import com.hrms.leave_service.entity.Leave;
import com.hrms.leave_service.repository.LeaveRepository;
import com.hrms.leave_service.utils.LeaveStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository repository;

    public LeaveServiceImpl(LeaveRepository repository) {
        this.repository = repository;
    }

    @Override
    public Leave applyLeave(Long employeeId, Leave leave) {

        leave.setEmployeeId(employeeId);
        leave.setStatus(LeaveStatus.PENDING);
        leave.setAppliedDate(LocalDate.now());

        return repository.save(leave);
    }

    @Override
    public Leave approveLeave(Long leaveId) {

        Leave leave = repository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        leave.setStatus(LeaveStatus.APPROVED);
        return repository.save(leave);
    }

    @Override
    public Leave rejectLeave(Long leaveId) {

        Leave leave = repository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        leave.setStatus(LeaveStatus.REJECTED);
        return repository.save(leave);
    }

    @Override
    public List<Leave> myLeaves(Long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    @Override
    public List<Leave> pendingLeaves() {
        return repository.findByStatus(LeaveStatus.PENDING);
    }
}
