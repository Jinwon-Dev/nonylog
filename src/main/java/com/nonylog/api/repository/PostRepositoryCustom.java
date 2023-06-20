package com.nonylog.api.repository;

import com.nonylog.api.domain.Post;
import com.nonylog.api.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
