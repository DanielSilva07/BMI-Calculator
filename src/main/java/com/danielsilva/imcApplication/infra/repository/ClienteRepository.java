package com.danielsilva.imcApplication.infra.repository;

import com.danielsilva.imcApplication.domain.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository  extends JpaRepository<ClienteModel, Long> {
}
