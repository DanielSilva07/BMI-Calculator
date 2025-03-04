package com.danielsilva.imcApplication.service;

import com.danielsilva.imcApplication.dto.ClienteDTO;
import com.danielsilva.imcApplication.model.ClienteModel;
import com.danielsilva.imcApplication.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final EmailClient emailClient;
    private final String exchange ;
    private final NotificationRabbitService notificationRabbitService;
    private final ClienteRepository repository;

    public ClienteService(@Value("${rabbitmq.imc-exchange}")String exchange,
                          NotificationRabbitService
                                  notificationRabbitService,
                          ClienteRepository repository) {
        this.exchange = exchange;
        this.notificationRabbitService = notificationRabbitService;
        this.repository = repository;
    }

    public ClienteModel save(ClienteDtoRequest clienteDtoRequest){
      ClienteModel clienteModel = new ClienteModel();

       clienteModel.setNome(clienteDtoRequest.nome());
       clienteModel.setAltura(clienteDtoRequest.altura());
       clienteModel.setPeso(clienteDtoRequest.peso());
       clienteModel.setEmailTo(clienteDtoRequest.emailTo());
       clienteModel.imcCalculator();
       notificationRabbitService.sendNotification(clienteDtoRequest,exchange);
       emailClient.sendEmail(notificationRabbitService.createMessageEmail(clienteModel));
        return repository.save(clienteModel);
    }

    public List<ClienteModel> getAll(){
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
