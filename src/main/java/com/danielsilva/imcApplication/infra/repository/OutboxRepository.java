package com.danielsilva.imcApplication.infra.repository;

import com.danielsilva.imcApplication.domain.Outbox;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, UUID> {
    
    /**
     * Finds all unprocessed outbox messages with pagination support.
     *
     * @param pageable pagination information
     * @return a page of unprocessed outbox messages
     */
    Page<Outbox>findByProcessedFalse(Pageable pageable);

}
