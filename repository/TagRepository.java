package com.lessonlink.repository;

import com.lessonlink.domain.post.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
