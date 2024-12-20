package com.lessonlink.application.service;

import com.lessonlink.domain.db.member.Member;
import com.lessonlink.domain.db.post.Post;
import com.lessonlink.domain.db.post.PostCategory;
import com.lessonlink.domain.db.post.PostTag;
import com.lessonlink.domain.db.post.Tag;
import com.lessonlink.dto.builder.PostDto;
import com.lessonlink.dto.info.PostInfoDto;
import com.lessonlink.exception.NotFoundException;
import com.lessonlink.repository.member.MemberRepository;
import com.lessonlink.repository.post.PostCategoryRepository;
import com.lessonlink.repository.post.PostRepository;
import com.lessonlink.repository.post.PostRepositoryCustomImpl;
import com.lessonlink.repository.post.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PostRepositoryCustomImpl postRepositoryCustomImpl;
    private final PostCategoryRepository postCategoryRepository;
    private final TagRepository tagRepository;

    /** 게시글 조회 */
    public Post findOne(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시글이 존재하지 않습니다. ID: " + postId));
    }

    /** 게시글 목록 조회 */
    public List<PostInfoDto> findAllByPostCategoryId(Long postCategoryId, Pageable pageable) {
        return postRepositoryCustomImpl.findAllByPostCategoryId(postCategoryId, pageable);
    }

    /** 게시글 등록 - memberIdSecretKey */
    @Transactional
    public Long postByMemberIdSecretKey(String memberIdSecretKey, Long postCategoryId, PostDto postDto, Long tagId) {
        //엔티티 조회
        Member member = memberRepository.findById(memberIdSecretKey)
                .orElseThrow(() -> new NotFoundException("회원이 존재하지 않습니다. ID: " + memberIdSecretKey));

        PostCategory postCategory = postCategoryRepository.findById(postCategoryId)
                .orElseThrow(() -> new NotFoundException("카테고리가 존재하지 않습니다. ID: " + postCategoryId));

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new NotFoundException("태그가 존재하지 않습니다. ID: " + tagId));

        //포스트 태그 생성
        PostTag postTag = PostTag.createPostTag(tag);

        //게시글 생성
        Post post = Post.createPost(member, postCategory, postDto, postTag);

        //게시글 저장
        postRepository.save(post);
        return post.getId();
    }

    /** 게시글 등록 - memberId */
    @Transactional
    public Long postByMemberId(String memberId, Long postCategoryId, PostDto postDto, Long tagId) {
        //엔티티 조회
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException("회원이 존재하지 않습니다. ID: " + memberId));

        PostCategory postCategory = postCategoryRepository.findById(postCategoryId)
                .orElseThrow(() -> new NotFoundException("카테고리가 존재하지 않습니다. ID: " + postCategoryId));

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new NotFoundException("태그가 존재하지 않습니다. ID: " + tagId));

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
    @Transactional
    public void deletePost(Long postId) {
        Post post = findOne(postId);
        postRepository.delete(post);
    }

}
