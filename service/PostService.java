package com.lessonlink.service;

import com.lessonlink.domain.member.Member;
import com.lessonlink.domain.post.Post;
import com.lessonlink.domain.post.PostCategory;
import com.lessonlink.domain.post.PostTag;
import com.lessonlink.domain.post.Tag;
import com.lessonlink.dto.PostDto;
import com.lessonlink.exception.NotFoundException;
import com.lessonlink.repository.MemberRepository;
import com.lessonlink.repository.PostCategoryRepository;
import com.lessonlink.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PostCategoryRepository postCategoryRepository;

    /** 게시글 조회 */
    public Post findOne(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시글이 존재하지 않습니다. ID: " + postId));
    }

    /** 게시글 등록 */
    @Transactional
    public Long post(String memberIdSecretKey, Long postCategoryId, PostDto postDto, Tag tag) {
        //엔티티 조회
        Member member = memberRepository.findById(memberIdSecretKey)
                .orElseThrow(() -> new NotFoundException("회원이 존재하지 않습니다. ID: " + memberIdSecretKey));

        PostCategory postCategory = postCategoryRepository.findById(postCategoryId)
                .orElseThrow(() -> new NotFoundException("카테고리가 존재하지 않습니다. ID: " + postCategoryId));

        //포스트 태그 생성
        PostTag postTag = PostTag.createPostTag(tag);

        //게시글 생성
        Post post = Post.createPost(member, postCategory, postDto, postTag);

        //게시글 저장
        postRepository.save(post);
        return post.getId();
    }

    /** 조회수 증가 */
    @Transactional
    public void incrementViews(Post post) {
        postRepository.incrementViews(post.getId());
    }

    /** 좋아요 */
    @Transactional
    public void incrementLikess(Post post) {
        postRepository.incrementLikes(post.getId());
    }

    /** 게시글 수정 */
    @Transactional
    public void updatePost(Long postId, PostDto postDto) {
        Post post = findOne(postId);
        post.updatePost(postDto);
    }

    /** 게시글 삭제 **/
    public void deletePost(Long postId) {
        Post post = findOne(postId);
        postRepository.delete(post);
    }

}
