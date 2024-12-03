package com.lessonlink.api.post;

import com.lessonlink.api.Result;
import com.lessonlink.dto.info.PostListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostListController {

    @GetMapping("/api/v1/posts/list/inbox/{memberIdSecretKey}")
    public ResponseEntity<List<PostListDto>> listSubscribingPosts
            (@PathVariable("memberIdSecretKey") String memberIdSecretKey) {
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/api/v1/posts/list/search")
    public ResponseEntity<List<PostListDto>> searchPosts
            (@RequestParam("query") String query) {
        return ResponseEntity.internalServerError().build();
    }
}
