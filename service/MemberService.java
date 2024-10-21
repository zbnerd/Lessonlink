package com.lessonlink.service;

import com.lessonlink.domain.common.Address;
import com.lessonlink.domain.member.Member;
import com.lessonlink.dto.AddressDto;
import com.lessonlink.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional
    public String signUp(Member member) {

        validateDuplicateMember(member); // 중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> foundMembers = memberRepository.findByMemberId(member.getMemberId());
        if (!foundMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    @Transactional
    public void updatePasswordAndEmail(String memberIdSecretKey, String password, String email) {
        Member member = memberRepository.findOne(memberIdSecretKey);
        member.updatePassword(password);
        member.updateEmail(email);
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(String id) {
        return memberRepository.findOne(id);
    }

    /**
     * 멤버 주소 수정
     */

    @Transactional
    public void updateAddress(String memberIdSecretKey, AddressDto addressDto) {
        Member member = memberRepository.findOne(memberIdSecretKey);
        member.setAddress(new Address(addressDto));
    }
}
