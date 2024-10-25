package com.lessonlink.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lessonlink.domain.common.BaseTimeEntity;
import com.lessonlink.domain.common.embedded.Address;
import com.lessonlink.domain.member.enums.Role;
import com.lessonlink.domain.order.Order;
import com.lessonlink.dto.MemberDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Getter
@Slf4j
@ToString
public class Member extends BaseTimeEntity {

    @Id @Column(name = "member_id_secret_key")
    private String id; // secret key 식별코드

    @Column(unique = true, nullable = false)
    private String memberId; // 실제 사용되는 멤버 id

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String name; // 이름

    private LocalDate birthDate; // 생일

    private String phoneNumber; // 휴대폰 번호

    private String email; // 이메일

    @Enumerated(EnumType.STRING)
    private Role role; // ADMIN : 관리자, TEACHER : 선생님, STUDENT : 학생

    @Setter
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

    public void setMemberInfo(MemberDto memberDto) {

        try {
            this.id = generateSecretKey();
            this.memberId = memberDto.getMemberId();
            this.password = memberDto.getPassword();
            this.name = memberDto.getName();
            this.birthDate = memberDto.getBirthDate();
            this.phoneNumber = memberDto.getPhoneNumber();
            this.email = memberDto.getEmail();
            this.role = memberDto.getRole();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    private static String generateSecretKey() throws NoSuchAlgorithmException {

        final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        final Random RANDOM = new Random();

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey secretKey = keyGenerator.generateKey();

        // SecretKey를 바이트 배열로 변환
        byte[] encoded = secretKey.getEncoded();

        // 바이트 배열을 알파벳 대소문자로만 변환
        StringBuilder sb = new StringBuilder();
        for (byte b : encoded) {
            // 0부터 255 사이의 값을 양수로 변환한 후, 알파벳 대소문자 중에서 선택
            sb.append(ALPHABET.charAt(Math.abs(RANDOM.nextInt(256)) % ALPHABET.length()));
        }

        return sb.toString();
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updatePassword(String password) {
        this.password = password;
    }


}
