package com.lessonlink.domain.redis.member;

import com.lessonlink.domain.db.common.embedded.Address;
import com.lessonlink.domain.db.member.Member;
import com.lessonlink.domain.db.member.enums.Role;
import com.lessonlink.dto.builder.MemberDto;
import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDate;

@RedisHash(value = "member", timeToLive = 7200)
@Getter
public class MemberRedis {

    @Id
    private String memberIdSecretKey; // secret key 식별코드
    private String memberId; // 실제 사용되는 멤버 id
    private String password;
    private String name; // 이름
    private LocalDate birthDate; // 생일
    private String phoneNumber; // 휴대폰 번호
    private String email; // 이메일
    private Role role; // ADMIN : 관리자, TEACHER : 선생님, STUDENT : 학생

    @Setter
    private Address address;

    public MemberRedis() {}

    public MemberRedis(Member member) {
        this.memberIdSecretKey = member.getId();
        this.memberId = member.getMemberId();
        this.password = member.getPassword();
        this.name = member.getName();
        this.birthDate = member.getBirthDate();
        this.phoneNumber = member.getPhoneNumber();
        this.email = member.getEmail();
        this.role = member.getRole();
        this.address = member.getAddress();
    }

    public Member getMember() {
        Member member = new Member();
        member.setMemberInfo(
                MemberDto.builder()
                        .memberId(this.memberId)
                        .password(this.password)
                        .name(this.name)
                        .birthDate(this.birthDate)
                        .phoneNumber(this.phoneNumber)
                        .email(this.email)
                        .role(this.role)
                        .build()
        );
        member.setAddress(this.address);
        return member;
    }
}
