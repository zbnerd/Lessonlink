package com.lessonlink.adapter.in.api.member;

import com.lessonlink.adapter.in.api.Result;
import com.lessonlink.domain.member.Member;
import com.lessonlink.domain.member.enums.Role;
import com.lessonlink.dto.MemberDto;
import com.lessonlink.exception.AuthenticationFailedException;
import com.lessonlink.application.service.MemberService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 새로운 회원을 생성하는 HTTP POST 요청을 처리합니다. 이 메서드는
     * 회원 정보를 포함하는 JSON 페이로드를 받아 `Member` 객체를 생성하고,
     * 입력값을 검증한 후 회원 가입을 시도합니다.
     * 회원 가입이 성공하면 생성된 회원의 ID를 응답으로 반환합니다.
     *
     * @param request 회원의 세부 정보를 포함하는 `CreateMemberRequest` 객체:
     *                - memberId: 회원의 고유 ID - 유니크
     *                - password: 회원의 비밀번호
     *                - name: 회원의 이름
     *                - birthDate: 회원의 생년월일
     *                - phoneNumber: 회원의 휴대폰 번호 - 유니크
     *                - email: 회원의 이메일 주소 - 유니크
     *                - role: 회원의 역할 (ADMIN, TEACHER, STUDENT)
     *
     * @return 생성된 회원의 고유 ID를 포함하는 `CreateMemberResponse` 객체
     */
    @PostMapping("/api/v1/members")
    public CreateMemberResponse signUpMember(
            @RequestBody @Valid CreateMemberRequest request
    ) {
        Member member = new Member();
        member.setMemberInfo(buildMemberInfo(request));
        String id = memberService.signUp(member);
        return new CreateMemberResponse(id);
    }

    /**
     * 회원 로그인 요청을 처리합니다. 회원의 ID와 비밀번호를 검증하고,
     * 로그인에 성공하면 해당 회원의 정보를 응답으로 반환합니다.
     *
     * @param request 회원의 로그인 정보를 포함한 `MemberLoginRequest` 객체
     * @return 로그인 성공 시 회원 정보를 포함한 `Result` 객체
     */
    @PostMapping("/api/v1/members/login")
    public Result loginMember(
            @RequestBody @Valid MemberLoginRequest request
    ) {
        boolean login = memberService.login(request.getMemberId(), request.getPassword());

        if(!login) {
            throw new AuthenticationFailedException("패스워드가 일치하지 않습니다.");
        }

        Member member = memberService.findOneByMemberId(request.getMemberId());
        return new Result(new MemberInfo(
                member.getMemberId(),
                member.getName(),
                member.getBirthDate(),
                member.getPhoneNumber(),
                member.getEmail(),
                member.getRole()
        ));
    }

    /**
     * 특정 회원의 비밀번호와 이메일을 업데이트하는 HTTP PUT 요청을 처리합니다.
     * 이 메서드는 요청으로 받은 식별자 ID와 새로운 비밀번호, 이메일을 통해
     * 회원 정보를 업데이트합니다. 업데이트가 완료되면, 수정된 회원 정보를
     * 응답으로 반환합니다.
     *
     * @param id 회원의 고유 식별자 (PathVariable로 전달)
     * @param request 비밀번호와 이메일 정보를 담고 있는 `UpdateMemberRequest` 객체
     *
     * @return 수정된 회원의 ID, 비밀번호, 이메일을 포함한 `UpdateMemberResponse` 객체
     */
    @PutMapping("/api/v1/members/{id}")
    public UpdateMemberResponse updateMember(
            @PathVariable String id,
            @RequestBody @Valid UpdateMemberRequest request
    ) {
        memberService.updatePasswordAndEmail(id, request.getPassword(), request.getEmail());

        Member foundMember = memberService.findOne(id);
        return new UpdateMemberResponse(foundMember.getId(), foundMember.getPassword(), foundMember.getEmail());
    }

    /**
     * 모든 회원 정보를 조회하는 HTTP GET 요청을 처리합니다.
     * 이 메서드는 회원 서비스로부터 전체 회원 목록을 조회한 후,
     * 각 회원의 ID, 이름, 생년월일, 휴대폰 번호, 이메일, 역할 정보를 담은
     * `MemberInfo` 객체 리스트로 변환하여 반환합니다.
     * 추후에 로그인 기능이 추가될 경우, 해당 기능은 관리자 권한 전용으로 설정될 수 있습니다.
     *
     * @return 전체 회원의 정보를 담은 `MemberInfo` 리스트를 포함한 `Result` 객체
     */
    @GetMapping("/api/v1/members")
    public Result allMembers() {
        List<Member> findMembers = memberService.findMembers();

        List<MemberInfo> collect = findMembers.stream()
                .map(m -> new MemberInfo(
                        m.getMemberId(),
                        m.getName(),
                        m.getBirthDate(),
                        m.getPhoneNumber(),
                        m.getEmail(),
                        m.getRole()
                )).toList();
        return new Result(collect);
    }

    /**
     * 특정 회원의 ID로 회원 정보를 조회하는 HTTP GET 요청을 처리합니다.
     * 이 메서드는 요청으로 전달된 회원 ID를 통해 해당 회원의 정보를 조회하고,
     * 조회된 회원 정보를 `Result` 객체로 반환합니다.
     * 추후에 로그인 기능이 구현될 경우, 자기 자신의 정보만 조회 가능하도록 변경될 수 있습니다.
     *
     * @param id 조회할 회원의 고유 식별자 (PathVariable로 전달)
     *
     * @return 조회된 회원의 정보를 담은 `Result` 객체
     */
    @GetMapping("/api/v1/members/{id}")
    public Result findMemberById(@PathVariable String id) {
        Member foundMember = memberService.findOne(id);
        return new Result(foundMember);
    }

    /**
     * 새로운 회원 생성을 위한 요청 데이터를 담는 클래스입니다.
     */
    @Data
    static class CreateMemberRequest {
        private String memberId; // 실제 사용되는 멤버 id
        private String password; // 비밀번호
        private String name; // 이름
        private LocalDate birthDate; // 생일
        private String phoneNumber; // 휴대폰 번호
        private String email; // 이메일
        private Role role; // ADMIN : 관리자, TEACHER : 선생님, STUDENT : 학생
    }

    /**
     * 회원 생성에 대한 응답 데이터를 담는 클래스입니다.
     */
    @Data
    @AllArgsConstructor
    static class CreateMemberResponse {
        private String id;
    }

    /**
     * 회원 업데이트 요청 데이터를 담는 클래스입니다.
     */
    @Data
    static class UpdateMemberRequest {
        private String password;
        private String email;
    }

    /**
     * 회원 업데이트에 대한 응답 데이터를 담는 클래스입니다.
     */
    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private String id;
        private String password;
        private String email;
    }

    /**
     * 회원 로그인 요청 데이터를 담는 클래스입니다.
     */
    @Data
    static class MemberLoginRequest {
        private String memberId;
        private String password;
    }

    /**
     * 회원 정보를 담는 클래스입니다.
     */
    @Data
    @AllArgsConstructor
    static class MemberInfo {
        private String memberId; // 실제 사용되는 멤버 id
        private String name; // 이름
        private LocalDate birthDate; // 생일
        private String phoneNumber; // 휴대폰 번호
        private String email; // 이메일
        private Role role; // ADMIN : 관리자, TEACHER : 선생님, STUDENT : 학생
    }

    /**
     * CreateMemberRequest의 데이터를 사용하여 MemberDto 객체를 생성합니다.
     *
     * @param request 회원 생성을 위한 요청 데이터를 포함하는 객체
     * @return MemberDto 객체
     */
    private MemberDto buildMemberInfo(CreateMemberRequest request) {

        return new MemberDto.Builder()
                .memberId(request.getMemberId())
                .password(request.getPassword())
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .role(request.getRole())
                .build();
    }

}
