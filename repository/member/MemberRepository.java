package com.lessonlink.repository.member;

import com.lessonlink.domain.db.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String>, MemberRepositoryCustom {

    @Query("select m from Member m where m.memberId = :memberId")
    Optional<Member> findByMemberId(@Param("memberId") String memberId);

}
