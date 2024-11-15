package com.lessonlink.application.service;

import com.lessonlink.domain.common.embedded.Address;
import com.lessonlink.domain.member.Member;
import com.lessonlink.dto.AddressDto;
import com.lessonlink.exception.DuplicateMemberException;
import com.lessonlink.exception.NotFoundException;
import com.lessonlink.adapter.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     */
    @Transactional
    public String signUp(Member member) {
        validateDuplicateMember(member); // 중복회원 검증
        member.updatePassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        Optional<Member> foundMembers = memberRepository.findByMemberId(member.getMemberId());
        if (foundMembers.isPresent()) {
            throw new DuplicateMemberException("이미 존재하는 회원입니다.");
        }
    }

    @Transactional
    public void updatePasswordAndEmail(String memberIdSecretKey, String password, String email) {
        Member member = findOne(memberIdSecretKey);

        member.updatePassword(passwordEncoder.encode(password));
        member.updateEmail(email);
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(String memberIdSecretKey) {
        return memberRepository.findById(memberIdSecretKey)
                .orElseThrow(() -> new NotFoundException("해당 회원이 존재하지 않습니다. ID: " + memberIdSecretKey));
    }

    public Member findOneByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException("해당 회원이 존재하지 않습니다. ID: " + memberId));
    }

    /**
     * 멤버 주소 수정
     */

    @Transactional
    public void updateAddress(String memberIdSecretKey, AddressDto addressDto) {
        Member member = findOne(memberIdSecretKey);
        member.setAddress(new Address(addressDto));

    }

    public boolean login(String memberId, String password) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException("해당 회원이 존재하지 않습니다. ID: " + memberId));

        return passwordEncoder.matches(password, member.getPassword());
    }

    @Transactional
    public void deleteByMemberId(String memberId) {
        Member member = findOneByMemberId(memberId);
        memberRepository.delete(member);
    }

    @Transactional
    public void deleteByMemberIdSecretKey(String memberIdSecretKey) {
        Member member = findOne(memberIdSecretKey);
        memberRepository.delete(member);
    }
}
