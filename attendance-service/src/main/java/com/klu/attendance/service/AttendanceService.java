package com.klu.attendance.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.klu.attendance.client.MemberClient;
import com.klu.attendance.dto.MemberResponse;
import com.klu.attendance.entity.Attendance;
import com.klu.attendance.repository.AttendanceRepository;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final MemberClient memberClient;

    public AttendanceService(
            AttendanceRepository attendanceRepository,
            MemberClient memberClient) {

        this.attendanceRepository = attendanceRepository;
        this.memberClient = memberClient;
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public Attendance getAttendanceById(Long id) {
        return attendanceRepository.findById(id).orElse(null);
    }

    public Attendance createAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public Attendance updateAttendance(Long id, Attendance attendance) {
        Attendance existing = attendanceRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setMemberId(attendance.getMemberId());
            existing.setDate(attendance.getDate());
            existing.setCheckInTime(attendance.getCheckInTime());
            existing.setCheckOutTime(attendance.getCheckOutTime());
            existing.setStatus(attendance.getStatus());

            return attendanceRepository.save(existing);
        }

        return null;
    }

    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }

    public MemberResponse getMemberDetails(Long memberId) {
        return memberClient.getMemberById(memberId);
    }
}