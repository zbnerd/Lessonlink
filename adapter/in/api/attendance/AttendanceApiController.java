package com.lessonlink.adapter.in.api.attendance;

import com.lessonlink.adapter.in.api.Result;
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

    /**
     * 학생의 출석을 생성합니다.
     * @param request 예약 ID를 포함한 출석 요청 데이터
     * @return 생성된 출석 ID를 포함한 응답
     */
    @PostMapping("/api/v1/attendances")
    public Result makeAttendance(
            @RequestBody @Valid MakeAttendanceRequestDto request
    ) {
        Long attendanceId = attendanceService.checkIn(request.getReservationId());
        return new Result(new MakeAttendanceResponseDto(attendanceId));
    }

    /**
     * 출석 상태를 '출석'으로 업데이트합니다.
     * @param attendanceId 업데이트할 출석의 ID
     * @return 업데이트된 출석 ID를 포함한 응답
     */
    @PatchMapping("/api/v1/attendances/{attendanceId}/present")
    public Result updateAttendanceStatusPresent(@PathVariable Long attendanceId) {
        attendanceService.updateAttendanceStatusToPresent(attendanceId);
        return new Result(new UpdateAttendanceStatusResponseDto(attendanceId));
    }

    /**
     * 출석 상태를 '지각'으로 업데이트합니다.
     * @param attendanceId 업데이트할 출석의 ID
     * @return 업데이트된 출석 ID를 포함한 응답
     */
    @PatchMapping("/api/v1/attendances/{attendanceId}/late")
    public Result updateAttendanceStatusLate(@PathVariable Long attendanceId) {
        attendanceService.updateAttendanceStatusToLate(attendanceId);
        return new Result(new UpdateAttendanceStatusResponseDto(attendanceId));
    }

    /**
     * 출석 상태를 '결석'으로 업데이트합니다.
     * @param attendanceId 업데이트할 출석의 ID
     * @return 업데이트된 출석 ID를 포함한 응답
     */
    @PatchMapping("/api/v1/attendances/{attendanceId}/absent")
    public Result updateAttendanceStatusAbsent(@PathVariable Long attendanceId) {
        attendanceService.updateAttendanceStatusToAbsent(attendanceId);
        return new Result(new UpdateAttendanceStatusResponseDto(attendanceId));
    }

    /**
     * 학생의 체크아웃 시간을 기록합니다.
     * @param attendanceId 체크아웃할 출석의 ID
     * @return 체크아웃 처리된 출석 ID를 포함한 응답
     */
    @PatchMapping("/api/v1/attendances/{attendanceId}/checkout")
    public Result updateCheckOut(@PathVariable Long attendanceId) {
        attendanceService.checkOut(attendanceId);
        return new Result(new UpdateAttendanceStatusResponseDto(attendanceId));
    }

    /**
     * 특정 강의에 속한 학생들의 출석 현황을 조회합니다.
     * @param courseId 조회할 강의의 ID
     * @return 학생 출석 정보를 포함한 응답
     */
    @GetMapping("/api/v1/courses/{courseId}/attendances")
    public Result getAttendances(@PathVariable Long courseId) {
        return new Result(attendanceService.getStudentAttendanceInfo(courseId));
    }

    /**
     * 출석 요청 데이터를 담는 DTO 클래스입니다.
     */
    @Data
    static class MakeAttendanceRequestDto {
        private Long reservationId;
    }

    /**
     * 출석 생성에 대한 응답 데이터를 담는 DTO 클래스입니다.
     */
    @Data
    @AllArgsConstructor
    static class MakeAttendanceResponseDto {
        private Long attendanceId;
    }

    /**
     * 출석 상태 업데이트에 대한 응답 데이터를 담는 DTO 클래스입니다.
     */
    @Data
    @AllArgsConstructor
    static class UpdateAttendanceStatusResponseDto {
        private Long attendanceId;
    }

}
