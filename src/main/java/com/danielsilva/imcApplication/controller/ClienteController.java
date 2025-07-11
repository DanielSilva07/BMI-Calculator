package com.danielsilva.imcApplication.controller;
import com.danielsilva.imcApplication.dtos.ClienteDtoRequest;
import com.danielsilva.imcApplication.domain.ClienteModel;
import com.danielsilva.imcApplication.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/bmi")
public class ClienteController {

    private final ClienteService service;

    public ClienteController( ClienteService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<ClienteModel>save(@RequestBody @Valid ClienteDtoRequest clienteDtoRequest){
        return ResponseEntity.status(201).body(service.save(clienteDtoRequest));
    }

    @GetMapping()
    public ResponseEntity<List<?>>getAll() {
        return ResponseEntity.ok().body(service.clientList());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object>deleteById(@PathVariable (value = "id") Long id) {
        return service.deleteById(id);
    }

}


