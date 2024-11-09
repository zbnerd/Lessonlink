package com.lessonlink.api.attendance;

import com.lessonlink.api.Result;
import com.lessonlink.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class AttendanceApiController {

    private final AttendanceService attendanceService;

    @PostMapping("/api/v1/attendances")
    public Result makeAttendance(
            @RequestBody @Valid MakeAttendanceRequestDto request
    ) {
        Long attendanceId = attendanceService.checkIn(request.getReservationId());
        return new Result(new MakeAttendanceResponseDto(attendanceId));
    }

    @PatchMapping("/api/v1/attendances/{attendanceId}/present")
    public Result updateAttendanceStatusPresent(@PathVariable Long attendanceId) {
        attendanceService.updateAttendanceStatusToPresent(attendanceId);
        return new Result(new UpdateAttendanceStatusResponseDto(attendanceId));
    }

    @PatchMapping("/api/v1/attendances/{attendanceId}/late")
    public Result updateAttendanceStatusLate(@PathVariable Long attendanceId) {
        attendanceService.updateAttendanceStatusToLate(attendanceId);
        return new Result(new UpdateAttendanceStatusResponseDto(attendanceId));
    }

    @PatchMapping("/api/v1/attendances/{attendanceId}/absent")
    public Result updateAttendanceStatusAbsent(@PathVariable Long attendanceId) {
        attendanceService.updateAttendanceStatusToAbsent(attendanceId);
        return new Result(new UpdateAttendanceStatusResponseDto(attendanceId));
    }

    @PatchMapping("/api/v1/attendances/{attendanceId}/checkout")
    public Result updateCheckOut(@PathVariable Long attendanceId) {
        attendanceService.checkOut(attendanceId);
        return new Result(new UpdateAttendanceStatusResponseDto(attendanceId));
    }

    @GetMapping("/api/v1/courses/{courseId}/attendances")
    public Result getAttendances(@PathVariable Long courseId) {
        return new Result(attendanceService.getStudentAttendanceInfo(courseId));
    }

    @Data
    static class MakeAttendanceRequestDto {
        private Long reservationId;
    }

    @Data
    @AllArgsConstructor
    static class MakeAttendanceResponseDto {
        private Long attendanceId;
    }

    @Data
    @AllArgsConstructor
    static class UpdateAttendanceStatusResponseDto {
        private Long attendanceId;
    }

}
