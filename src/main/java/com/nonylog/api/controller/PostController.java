package com.nonylog.api.controller;

import com.nonylog.api.request.CreatePostRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class PostController {

    @PostMapping("/posts")
    public String post(@RequestBody CreatePostRequest params) {

        log.info("params={}", params.toString());
        return "Hello World";
    }
}
