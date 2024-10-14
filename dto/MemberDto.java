package com.lessonlink.dto;

import com.lessonlink.domain.member.Role;
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
public class MemberDto {
    private String memberId;
    private String password;
    private String name;
    private LocalDate birthDate;
    private String phoneNumber;
    private String email;
    private Role role;

    // `Builder` 클래스에서 전달된 값을 통해 `MemberDto` 객체를 생성하는 생성자

    private MemberDto(Builder builder) {
        this.memberId = builder.memberId;
        this.password = builder.password;
        this.name = builder.name;
        this.birthDate = builder.birthDate;
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;
        this.role = builder.role;
    }

    /**
     * 회원 정보를 설정하는 빌더 클래스.
     * 빌더 패턴을 통해 각 필드를 설정하고, `build()` 메서드를 통해
     * `MemberDto` 객체를 생성합니다.
     */
    public static class Builder {
        private String memberId;
        private String password;
        private String name;
        private LocalDate birthDate;
        private String phoneNumber;
        private String email;
        private Role role;

        public Builder memberId(String memberId) {
            this.memberId = memberId;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            log.info("MemberDto role = {}", this.role);
            return this;
        }

        public MemberDto build() {
            return new MemberDto(this);
        }
    }
}
