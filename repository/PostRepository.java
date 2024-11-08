package com.lessonlink.repository;

import com.lessonlink.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update Post p set p.views = p.views +1 where p.id = :postId")
    void incrementViews(Long postId);
}
