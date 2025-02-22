package com.danielsilva.imcApplication.infra;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    private final String exchangeName;
    private final ConnectionFactory connectionFactory;

    public RabbitMQConfig(@Value("${rabbitmq.imc-exchange}")String exchangeName,
                          ConnectionFactory connectionFactory1) {
        this.exchangeName = exchangeName;
        this.connectionFactory = connectionFactory1;
    }

    @Bean
    public Queue criarFilaImc( ) {
        return QueueBuilder.durable ("imc-microservice").build();
    }

    @Bean
    public RabbitAdmin CriarRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializarAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public FanoutExchange criarExchangeImc() {
        return ExchangeBuilder.fanoutExchange(exchangeName).build();
    }

    @Bean
    public Binding criarBindingImc() {
        return BindingBuilder.bind(criarFilaImc()).to(criarExchangeImc());
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}
