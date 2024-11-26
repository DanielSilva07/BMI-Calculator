package com.danielsilva.imcApplication.controller;

import com.danielsilva.imcApplication.dto.ClienteDTO;
import com.danielsilva.imcApplication.model.Cliente;
import com.danielsilva.imcApplication.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/bmi")
public class Controller {

    private final ClienteService service;

    public Controller(ClienteService service) {
        this.service = service;
    }

    @PostMapping("/calculate")
    public ResponseEntity<Cliente> save(@RequestBody ClienteDTO clienteDTO ){
        return ResponseEntity.status(201).body(service.save(clienteDTO));
    }

    @GetMapping("/history")
    public ResponseEntity<List<Cliente>>getAll(){
        return ResponseEntity.ok().body(service.getAll());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable (value = "id") String id) {
        return service.deleteById(id);
    }

}


