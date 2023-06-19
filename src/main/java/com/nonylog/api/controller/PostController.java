package com.nonylog.api.controller;

import com.nonylog.api.request.CreatePostRequest;
import com.nonylog.api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid final CreatePostRequest request) {

        postService.write(request);
        return Map.of();
    }
}
