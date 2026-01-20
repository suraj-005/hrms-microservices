package com.hrms.leave_service.repository;

import com.hrms.leave_service.entity.Leave;
import com.hrms.leave_service.utils.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {

    List<Leave> findByEmployeeId(Long employeeId);

    List<Leave> findByStatus(LeaveStatus status);
}
