package com.lessonlink.repository;

import com.lessonlink.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    @Query("select m from Member m where m.memberId = :memberId")
    Optional<Member> findByMemberId(@Param("memberId") String memberId);

}
