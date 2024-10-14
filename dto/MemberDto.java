package com.lessonlink.dto;

import com.lessonlink.domain.member.Role;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

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

    private MemberDto(Builder builder) {
        this.memberId = builder.memberId;
        this.password = builder.password;
        this.name = builder.name;
        this.birthDate = builder.birthDate;
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;
        this.role = builder.role;
    }

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
