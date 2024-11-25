package com.danielsilva.imcApplication.repository;

import com.danielsilva.imcApplication.model.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository  extends MongoRepository<Cliente,String> {
}
