package com.lessonlink.repository.post;

import com.lessonlink.domain.db.post.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}