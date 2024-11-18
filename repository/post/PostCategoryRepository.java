package com.lessonlink.repository.post;

import com.lessonlink.domain.db.post.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
}
