package com.danielsilva.imcApplication.service;

import com.danielsilva.imcApplication.domain.ClienteModel;
import com.danielsilva.imcApplication.dtos.ClienteDtoRequest;
import com.danielsilva.imcApplication.dtos.ClienteDtoResponse;
import com.danielsilva.imcApplication.infra.kafka.MessageProducer;
import com.danielsilva.imcApplication.infra.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    
    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);
    
    private final MessageProducer messageProducer;
    private final ClienteRepository repository;

    public ClienteService(MessageProducer messageProducer,
                          ClienteRepository repository) {
        this.messageProducer = messageProducer;
        this.repository = repository;
    }

    @CacheEvict(value = "listaDeClientes", allEntries = true)
    public ClienteModel save(ClienteDtoRequest clienteDtoRequest) {
        if (clienteDtoRequest == null) {
            throw new IllegalArgumentException("O objeto ClienteDtoRequest não pode ser nulo");
        }
        try {
            logger.info("Limpando cache de clientes...");
            ClienteModel clienteModel = new ClienteModel();
            clienteModel.setNome(clienteDtoRequest.getNome());
            clienteModel.setAltura(clienteDtoRequest.getAltura());
            clienteModel.setPeso(clienteDtoRequest.getPeso());
            clienteModel.setEmail(clienteDtoRequest.getEmail());
            clienteModel.imcCalculator();
            // messageProducer.sendMessage("imc", clienteModel.getImc().toString());
            return repository.save(clienteModel);
        } catch (Exception e) {
            logger.error("Erro ao salvar cliente: " + e.getMessage(), e);
            throw new RuntimeException("Erro ao processar o cadastro do cliente", e);
        }

   }

   @Cacheable(value = "listaDeClientes")
    public List<ClienteDtoResponse> clientList(){
        logger.info("Buscando clientes no banco de dados...");
        return repository.findAll().stream()
                .map(clienteModel -> ClienteDtoResponse.builder()
                        .id(clienteModel.getId())
                        .nome(clienteModel.getNome())
                        .email(clienteModel.getEmail())
                        .altura(clienteModel.getAltura())
                        .peso(clienteModel.getPeso())
                        .imc(clienteModel.getImc())
                        .build())
                .collect(Collectors.toList());
    }

    public ResponseEntity<Object>deleteById(Long id) {
        return repository.findById(id)
                .map(taskToDelete ->{
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
