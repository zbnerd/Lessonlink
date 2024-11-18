package com.lessonlink.repository.member;

import com.lessonlink.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryBulk implements MemberRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    public void bulkInsertMembers(List<Member> members) {

        String sql = "INSERT INTO member (member_id_secret_key, member_id, password, name, birth_date, phone_number, email, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Member member = members.get(i);

                ps.setString(1, member.getId()); // Secret Key를 ID로 설정
                ps.setString(2, member.getMemberId());
                ps.setString(3, member.getPassword());
                ps.setString(4, member.getName());
                ps.setDate(5, member.getBirthDate() != null ? Date.valueOf(member.getBirthDate()) : null);
                ps.setString(6, member.getPhoneNumber());
                ps.setString(7, member.getEmail());
                ps.setString(8, member.getRole() != null ? member.getRole().toString() : null);
            }

            @Override
            public int getBatchSize() {
                return members.size();
            }
        });
    }
}
