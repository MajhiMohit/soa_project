package com.klu.attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klu.attendance.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}