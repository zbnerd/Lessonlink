package com.lessonlink.repository.member;


import com.lessonlink.domain.redis.member.MemberRedis;
import org.springframework.data.repository.CrudRepository;

public interface MemberRedisRepository extends MemberRepositoryCustom, CrudRepository<MemberRedis, String> {

}
