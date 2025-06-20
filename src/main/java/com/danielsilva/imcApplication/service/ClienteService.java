package com.danielsilva.imcApplication.service;

import com.danielsilva.imcApplication.domain.ClienteModel;
import com.danielsilva.imcApplication.dtos.ClienteDtoRequest;
import com.danielsilva.imcApplication.dtos.ClienteDtoResponse;
import com.danielsilva.imcApplication.infra.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
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
    
    private final ClienteRepository repository;
    private final OutboxService outboxService;
    private final ObjectMapper objectMapper;

    public ClienteService(ClienteRepository repository, OutboxService outboxService, ObjectMapper objectMapper) {
        this.repository = repository;
        this.outboxService = outboxService;
        this.objectMapper = objectMapper;
    }



    @Transactional
    @CacheEvict(value = "listaDeClientes", allEntries = true)
    public ClienteModel save(ClienteDtoRequest clienteDtoRequest) {
        if (clienteDtoRequest == null) {
            throw new IllegalArgumentException("O Objeto cliente não pode ser nulo");
        }
        try {
            logger.info("Salvando cliente...");
            ClienteModel clienteModel = new ClienteModel();
            clienteModel.setNome(clienteDtoRequest.getNome());
            clienteModel.setAltura(clienteDtoRequest.getAltura());
            clienteModel.setPeso(clienteDtoRequest.getPeso());
            clienteModel.setEmail(clienteDtoRequest.getEmail());
            clienteModel.imcCalculator();
            
            ClienteModel savedCliente = repository.save(clienteModel);

            // Em vez de enviar direto para o Kafka, salva no outbox
            outboxService.saveToOutbox(savedCliente, savedCliente.getId().toString());
            logger.info("Cliente salvo e mensagem adicionada ao outbox");
            
            return savedCliente;

        } catch (Exception e) {
            logger.error("Erro ao salvar cliente: {}", e.getMessage(), e);
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
