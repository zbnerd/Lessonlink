package com.lessonlink.service;

import com.lessonlink.domain.common.embedded.Address;
import com.lessonlink.domain.member.Member;
import com.lessonlink.dto.AddressDto;
import com.lessonlink.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        Optional<Member> foundMembers = memberRepository.findByMemberId(member.getMemberId());
        if (foundMembers.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    @Transactional
    public void updatePasswordAndEmail(String memberIdSecretKey, String password, String email) {
        Optional<Member> member = memberRepository.findById(memberIdSecretKey);
        member.ifPresent(gotMember -> gotMember.updatePassword(password));
        member.ifPresent(gotMember -> gotMember.updateEmail(email));
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(String memberIdSecretKey) {
        return memberRepository.findById(memberIdSecretKey)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다. ID: " + memberIdSecretKey));
    }

    /**
     * 멤버 주소 수정
     */

    @Transactional
    public void updateAddress(String memberIdSecretKey, AddressDto addressDto) {
        Optional<Member> member = memberRepository.findById(memberIdSecretKey);
        member.ifPresent(gotMember -> gotMember.setAddress(new Address(addressDto)));

    }
}
