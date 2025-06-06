package com.danielsilva.imcApplication.clients;

import com.danielsilva.imcApplication.dtos.EmailModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class EmailClient {

    @Value("${ms.api.email.url}")
    String baseUrlEmail;

    final RestClient restClient;

    public EmailClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public void sendEmail(EmailModel emailModel) {
        String url = baseUrlEmail ;

        try {
          restClient.post()
                  .uri(url)
                  .contentType(MediaType.APPLICATION_JSON)
                  .body(emailModel)
                  .retrieve()
                  .toBodilessEntity();

        } catch (RestClientException e) {
            throw new RuntimeException("Não foi possível enviar o email para o usuário", e);
        }
    }




}
