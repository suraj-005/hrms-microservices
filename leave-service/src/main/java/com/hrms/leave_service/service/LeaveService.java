package com.hrms.leave_service.service;

import com.hrms.leave_service.entity.Leave;

import java.util.List;

public interface LeaveService {

    Leave applyLeave(Long employeeId, Leave leave);

    Leave approveLeave(Long leaveId);

    Leave rejectLeave(Long leaveId);

    List<Leave> myLeaves(Long employeeId);

    List<Leave> pendingLeaves();
}
