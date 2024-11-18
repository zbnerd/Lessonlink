package com.lessonlink.dto.builder;

import com.lessonlink.domain.member.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;


/**
 * 회원 정보를 담고 있는 `MemberDto` 클래스.
 * 빌더 패턴을 사용하여 회원 객체를 생성하며, 회원의 ID, 비밀번호, 이름, 생년월일,
 * 휴대폰 번호, 이메일, 역할을 설정할 수 있습니다.
 * 클래스 내부에는 `Builder` 클래스가 있어 각 필드를 설정한 후
 * `build()` 메서드를 통해 `MemberDto` 객체를 생성할 수 있습니다.
 *
 */

@Getter
@Slf4j
@Builder
public class MemberDto {
    private String memberId;
    private String password;
    private String name;
    private LocalDate birthDate;
    private String phoneNumber;
    private String email;
    private Role role;
}
