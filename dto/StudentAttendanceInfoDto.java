package com.lessonlink.dto;

import com.lessonlink.domain.attendance.embedded.AttendanceDateTime;
import com.lessonlink.domain.attendance.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentAttendanceInfoDto {
    private String studentMemberIdSecretKey;
    private AttendanceStatus attendanceStatus;
    private AttendanceDateTime attendanceDateTime;
    private String courseName;
}
