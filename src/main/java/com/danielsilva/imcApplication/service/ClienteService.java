package com.danielsilva.imcApplication.service;

import com.danielsilva.imcApplication.clients.EmailClient;
import com.danielsilva.imcApplication.dtos.ClienteDtoRequest;
import com.danielsilva.imcApplication.dtos.ClienteDtoResponse;
import com.danielsilva.imcApplication.model.ClienteModel;
import com.danielsilva.imcApplication.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final EmailClient emailClient;
    private final String exchange ;
    private final NotificationRabbitService notificationRabbitService;
    private final ClienteRepository repository;

    public ClienteService(EmailClient emailClient, @Value("${rabbitmq.imc-exchange}")String exchange,
                          NotificationRabbitService notificationRabbitService,
                          ClienteRepository repository) {
        this.emailClient = emailClient;
        this.exchange = exchange;
        this.notificationRabbitService = notificationRabbitService;
        this.repository = repository;
    }

    public ClienteModel save(ClienteDtoRequest clienteDtoRequest){
      ClienteModel clienteModel = new ClienteModel();

       clienteModel.setNome(clienteDtoRequest.getNome());
       clienteModel.setAltura(clienteDtoRequest.getAltura());
       clienteModel.setPeso(clienteDtoRequest.getPeso());
       clienteModel.setEmail(clienteDtoRequest.getEmail());
       clienteModel.imcCalculator();
       notificationRabbitService.sendNotification(clienteModel,exchange);
//       emailClient.sendEmail(notificationRabbitService.createMessageEmail(clienteModel));
        return repository.save(clienteModel);
   }

    public List<ClienteDtoResponse> getAll(){
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

    public ResponseEntity<Object> deleteById(String id) {
        return repository.findById(id)
                .map(taskToDelete ->{
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
