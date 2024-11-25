package com.danielsilva.imcApplication.service;

import com.danielsilva.imcApplication.dto.ClienteDTO;
import com.danielsilva.imcApplication.model.Cliente;
import com.danielsilva.imcApplication.repository.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }


    public Cliente save(@org.jetbrains.annotations.NotNull ClienteDTO clienteDTO){
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.nome());
        cliente.setPeso(clienteDTO.peso());
        cliente.setAltura(clienteDTO.altura());
        cliente.imcCalculator();
        return repository.save(cliente);
    }

    public List<Cliente> getAll(){
        return repository.findAll();
    }


    public ResponseEntity<Object> deleteById(String id) {
        return repository.findById(id)
                .map(taskToDelete ->{
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
