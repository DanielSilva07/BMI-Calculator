package com.danielsilva.imcApplication.service;

import com.danielsilva.imcApplication.model.ClienteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationRabbitService {

    private final Logger log = LoggerFactory.getLogger(NotificationRabbitService.class);

    public final String exchange;

    private final RabbitTemplate rabbitTemplate;

    public NotificationRabbitService(
            @Value("${rabbitmq.imc-exchange}")String exchange,
                                     RabbitTemplate rabbitTemplate) {
        this.exchange = exchange;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNotification(ClienteModel clienteModel, String exchange) {
        try {
            log.info("Enviando Notificação para o Exchange {}", exchange);
            rabbitTemplate.convertAndSend(exchange, "", clienteModel);
        } catch (RuntimeException exception) {
            throw new RuntimeException(exception);
        }
    }

    public EmailModel createMessageEmail(ClienteModel clienteModel) {
        return new EmailModel(
                clienteModel.getId(),
                clienteModel.getEmailFrom(),
                clienteModel.getEmailTo(),
                 "Teste de envio de email",
                 "Olá " + clienteModel.getNome()  + ',' + "\n\n" + """
                 Seu IMC é :""" + clienteModel.getImc());

    }
}
