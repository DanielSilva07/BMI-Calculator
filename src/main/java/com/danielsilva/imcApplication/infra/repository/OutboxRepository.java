package com.danielsilva.imcApplication.infra.repository;

import com.danielsilva.imcApplication.domain.Outbox;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OutboxRepository extends CrudRepository<Outbox, UUID> {

    List<Outbox> findByAggregateIdOrderByCreatedAtDesc(String aggregateId);
    Page<Outbox>findByProcessedFalse(Pageable pageable);

}
