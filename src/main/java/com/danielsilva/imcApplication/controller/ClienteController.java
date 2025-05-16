package com.danielsilva.imcApplication.controller;
import com.danielsilva.imcApplication.dtos.ClienteDtoRequest;
import com.danielsilva.imcApplication.model.ClienteModel;
import com.danielsilva.imcApplication.service.ClienteService;
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
    public ResponseEntity<ClienteModel>save(@RequestBody ClienteDtoRequest clienteDtoRequest){
        return ResponseEntity.status(201).body(service.save(clienteDtoRequest));
    }

    @GetMapping()
    public ResponseEntity<List<ClienteModel>>getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable (value = "id") String id) {
        return service.deleteById(id);
    }

}


