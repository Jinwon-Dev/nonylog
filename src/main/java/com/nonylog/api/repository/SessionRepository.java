package com.nonylog.api.repository;

import com.nonylog.api.domain.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Long> {
}
