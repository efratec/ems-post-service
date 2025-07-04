package br.com.algapost.api.controller;

import br.com.algapost.api.common.mappers.PostMapper;
import br.com.algapost.api.model.PostInput;
import br.com.algapost.api.model.PostOutput;
import br.com.algapost.api.model.PostSummaryOutput;
import br.com.algapost.domain.model.PostId;
import br.com.algapost.domain.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private static final PostMapper postMapper = PostMapper.INSTANCE;

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> created(@RequestBody @Valid PostInput input) {
        var postOutput = postService.created(postMapper.toEntity(input));
        var location = URI.create("api/posts/" + postOutput.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostOutput> findById(@PathVariable String postId) {
        return ResponseEntity.ok(postService.findById(PostId.of(postId)));
    }

    @GetMapping
    public ResponseEntity<Page<PostSummaryOutput>> findAllSummarized(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(postService.findAllSummarized(pageable));
    }

}
