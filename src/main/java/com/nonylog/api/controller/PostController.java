package com.nonylog.api.controller;

import com.nonylog.api.request.CreatePostRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
public class PostController {

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid final CreatePostRequest params) {

        return Map.of();
    }
}
