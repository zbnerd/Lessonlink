package com.lessonlink.repository;

import com.lessonlink.dto.PostInfoDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.lessonlink.domain.post.QPost.post;
import static com.lessonlink.domain.post.QPostCategory.postCategory;


@Repository
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public PostRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<PostInfoDto> findAllByPostCategoryId(Long pageCategoryId, Pageable pageable) {
        return query
                .select(Projections.constructor(PostInfoDto.class,
                        post.member.name,
                        post.title,
                        post.contents,
                        post.isNotice,
                        post.createdAt,
                        post.views,
                        post.likes,
                        postCategory.categoryName
                        ))
                .from(post)
                .join(post.postCategory, postCategory)
                .where(postCategory.id.eq(pageCategoryId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
