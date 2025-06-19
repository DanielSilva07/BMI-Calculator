# Diagrama de Arquitetura da Aplicação IMC

```mermaid
%%{init: {'theme': 'base', 'themeVariables': { 'primaryColor': '#bbdefb','primaryTextColor': '#000000','primaryBorderColor': '#000000','lineColor': '#000000','secondaryColor': '#e3f2fd','tertiaryColor': '#bbdefb'}}}%%
graph TD
    subgraph "Cliente"
        A[Usuário] -->|HTTP/HTTPS| B[NGINX]
    end

    subgraph "Aplicação IMC (Spring Boot)"
        B -->|Proxy Reverso| C[Controlador REST]
        C -->|Chama| D[Serviço de Cálculo IMC]
        D -->|Lê/Grava| E[Repositório JPA]
        D -->|Cache| F[(Redis)]
        D -->|Salva| O[Outbox Table]
        O -->|Processa| P[Outbox Service]
        P -->|Publica| G[Kafka Producer]
    end

    subgraph "Infraestrutura"
        E -->|Persiste| H[(PostgreSQL)]
        G -->|Envia| I[Tópico Kafka]
        I -->|Consome| J[Consumidor Kafka]
    end

    style A fill:#e1f5fe,stroke:#000,stroke-width:2px
    style B fill:#bbdefb,stroke:#000,stroke-width:2px
    style C fill:#c8e6c9,stroke:#000,stroke-width:2px
    style D fill:#c8e6c9,stroke:#000,stroke-width:2px
    style E fill:#d1c4e9,stroke:#000,stroke-width:2px
    style F fill:#ffccbc,stroke:#000,stroke-width:2px
    style G fill:#d7ccc8,stroke:#000,stroke-width:2px
    style H fill:#b3e5fc,stroke:#000,stroke-width:2px
    style I fill:#d7ccc8,stroke:#000,stroke-width:2px
    style J fill:#d7ccc8,stroke:#000,stroke-width:2px
```

## Descrição dos Componentes

### Cliente
- **Usuário**: Acessa a aplicação através de um navegador web
- **NGINX**: Servidor web que atua como proxy reverso para a aplicação

### Aplicação IMC (Spring Boot)
- **Controlador REST**: Recebe as requisições HTTP e as direciona para o serviço apropriado
- **Serviço de Cálculo IMC**: Contém a lógica de negócio para cálculo do IMC
- **Repositório JPA**: Gerencia o acesso aos dados no banco de dados
- **Redis**: Cache para melhorar o desempenho das consultas frequentes
- **Tabela Outbox**: Armazena mensagens que precisam ser enviadas para o Kafka em uma transação atômica
- **Outbox Service**: Processa mensagens pendentes na tabela Outbox e as envia para o Kafka
- **Kafka Producer**: Envia mensagens para o sistema de mensageria

### Infraestrutura
- **PostgreSQL**: Banco de dados relacional para armazenamento persistente
- **Tópico Kafka**: Fila de mensagens para processamento assíncrono
- **Consumidor Kafka**: Processa as mensagens do tópico Kafka

## Padrão Outbox
O sistema implementa o padrão Outbox para garantir a entrega confiável de mensagens ao Kafka. Este padrão garante que as mensagens sejam entregues mesmo em caso de falhas na aplicação.

### Fluxo do Padrão Outbox:
1. **Armazenamento na Tabela Outbox**:
   - Quando um evento precisa ser publicado, ele é primeiro salvo na tabela Outbox dentro da mesma transação do banco de dados
   - Isso garante atomicidade entre a operação de negócio e o registro da mensagem

2. **Processamento Assíncrono**:
   - O Outbox Service verifica periodicamente a tabela Outbox por mensagens não processadas
   - Para cada mensagem não processada, o serviço tenta publicá-la no Kafka
   - Após publicação bem-sucedida, a mensagem é marcada como processada

3. **Tolerância a Falhas**:
   - Se o Kafka estiver indisponível, as mensagens permanecem na tabela Outbox
   - O serviço continuará tentando enviar as mensagens em intervalos regulares
   - O processamento é idempotente, então mensagens duplicadas não causam problemas

## Fluxo de Dados
1. O usuário acessa a aplicação através do navegador
2. O NGINX faz o proxy reverso para a aplicação Spring Boot
3. O Controlador REST recebe a requisição e a encaminha para o Serviço
4. O Serviço pode:
   - Consultar o cache (Redis) para respostas em cache
   - Acessar o banco de dados através do Repositório JPA
   - Salvar mensagens na tabela Outbox para processamento assíncrono
5. O Outbox Service processa as mensagens pendentes e as publica no Kafka
6. O Consumidor Kafka processa as mensagens do tópico conforme necessário
