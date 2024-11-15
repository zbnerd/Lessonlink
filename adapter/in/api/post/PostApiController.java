package com.lessonlink.adapter.in.api.post;

import com.lessonlink.adapter.in.api.Result;
import com.lessonlink.domain.post.Post;
import com.lessonlink.dto.PostDto;
import com.lessonlink.application.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostApiController {


    private final PostService postService;

    @PostMapping("/api/v1/posts")
    public Result createPost(@RequestBody CreatePostRequestDto request) {

        Long postId = postService.postByMemberId(
                request.getMemberId(),
                request.getPostCategoryId(),
                new PostDto.Builder()
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

//    / 테스트 필요!!
//    @PutMapping("/api/v1/posts/{postId}")
//    public Result updatePost(
//            @PathVariable Long postId,
//            @RequestBody UpdatePostRequestDto request
//    ) {
//        Post post = postService.findOne(postId);
//        post.updatePost(new PostDto.Builder()
//                .title(request.getTitle())
//                .isNotice(request.getIsNotice())
//                .contents(request.getContents())
//                .build());
//
//        return new Result(new PostResponseDto(postId));
//    }

//    / 삭제가 안되는 오류발견. 원인파악 필요
//    @DeleteMapping("/api/v1/posts/{postId}")
//    public Result deletePost(@PathVariable Long postId) {
//        postService.deletePost(postId);
//        return new Result(new PostResponseDto(postId));
//    }
//
//    / 테스트 필요!!
//    @GetMapping("/api/v1/posts/categories/{categoryId}")
//    public Result getPostsByCategory(
//            @PathVariable Long categoryId,
//            Pageable pageable
//    ) {
//        List<PostInfoDto> posts = postService.findAllByPostCategoryId(categoryId, pageable);
//        return new Result(posts);
//    }

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
