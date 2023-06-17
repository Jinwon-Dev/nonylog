package com.nonylog.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CreatePostRequest {

    @NotBlank(message = "타이틀을 입력해주세요.") // null도 함께 체크
    private String title;

    @NotBlank(message = "콘텐츠를 입력해주세요.")
    private String content;
}
