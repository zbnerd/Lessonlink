package com.lessonlink.dto.info;

import com.lessonlink.domain.db.attendance.embedded.AttendanceDateTime;
import com.lessonlink.domain.db.attendance.enums.AttendanceStatus;
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
