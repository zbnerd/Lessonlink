package com.lessonlink.adapter.repository;

import com.lessonlink.domain.post.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
}
