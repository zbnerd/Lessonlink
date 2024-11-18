package com.lessonlink.api.post;

import com.lessonlink.api.Result;
import com.lessonlink.domain.db.post.Post;
import com.lessonlink.dto.builder.PostDto;
import com.lessonlink.application.service.PostService;
import com.lessonlink.dto.info.PostInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostApiController {


    private final PostService postService;

    @PostMapping("/api/v1/posts")
    public Result createPost(@RequestBody CreatePostRequestDto request) {

        Long postId = postService.postByMemberId(
                request.getMemberId(),
                request.getPostCategoryId(),
                PostDto.builder()
                        .title(request.getTitle())
                        .isNotice(request.getIsNotice())
                        .contents(request.getContents())
                        .build(),
                request.tagId
        );

        return new Result(new PostResponseDto(postId));
    }

    @PatchMapping("/api/v1/posts/{postId}/views")
    public Result incrementViews(@PathVariable Long postId) {
        Post post = postService.findOne(postId);
        postService.incrementViews(post);

        return new Result(new PostResponseDto(postId));
    }

    @PatchMapping("/api/v1/posts/{postId}/like")
    public Result likePost(@PathVariable Long postId) {
        Post post = postService.findOne(postId);
        postService.incrementLikess(post);

        return new Result(new PostResponseDto(postId));
    }


    @PutMapping("/api/v1/posts/{postId}")
    public Result updatePost(
            @PathVariable Long postId,
            @RequestBody UpdatePostRequestDto request
    ) {
        postService.updatePost(postId, PostDto.builder()
                .title(request.getTitle())
                .isNotice(request.getIsNotice())
                .contents(request.getContents())
                .build());

        return new Result(new PostResponseDto(postId));
    }

    @DeleteMapping("/api/v1/posts/{postId}")
    public Result deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new Result(new PostResponseDto(postId));
    }

    @GetMapping("/api/v1/posts/categories/{categoryId}")
    public Result getPostsByCategory(
            @PathVariable Long categoryId,
            Pageable pageable
    ) {
        List<PostInfoDto> posts = postService.findAllByPostCategoryId(categoryId, pageable);
        return new Result(posts);
    }

    @Data
    static class CreatePostRequestDto {
        private String memberId;
        private Long postCategoryId;
        private String title;
        private Boolean isNotice;
        private String contents;
        private Long tagId;
    }

    @Data
    static class UpdatePostRequestDto {
        private String title;
        private Boolean isNotice;
        private String contents;
    }

    @Data
    @AllArgsConstructor
    static class PostResponseDto {
        private Long postId;
    }
}
