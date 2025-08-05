# Calculadora de IMC (Índice de Massa Corporal)

Este é um projeto de API RESTful para cálculo de IMC (Índice de Massa Corporal) desenvolvido com Spring Boot. O sistema permite calcular o IMC de usuários, armazenar o histórico de cálculos e fornecer análises.

## 🚀 Tecnologias

- **Backend**: Java 17, Spring Boot 3.x
- **Banco de Dados**: PostgreSQL
- **Cache**: Redis
- **Mensageria**: Apache Kafka
- **Proxy Reverso**: NGINX
- **Containerização**: Docker e Docker Compose
- **Gerenciamento de Dependências**: Maven


## 🌐 Endpoints da API

### Calcular IMC
- **POST** `/bmi`
  - Calcula o IMC com base nos dados fornecidos
  
  **Exemplo de Requisição:**
  ```json
  {
      "nome": "João",
      "altura": 1.75,
      "peso": 70
  }
  ```

  **Resposta de Sucesso (200 OK):**
  ```json
  {
      "id": "12345abcde",
      "nome": "João",
      "altura": 1.75,
      "peso": 70.0,
      "imc": 22.86
  }
  ```

### Histórico de Cálculos
- **GET** `/bmi`
  - Retorna todo o histórico de cálculos armazenados

  **Resposta de Sucesso (200 OK):**
  ```json
  [
      {
          "id": "12345abcde",
          "nome": "João",
          "altura": 1.75,
          "peso": 70.0,
          "imc": 22.86
      },
      {
          "id": "67890fghij",
          "nome": "Maria",
          "altura": 1.65,
          "peso": 60.0,
          "imc": 22.04
      }
  ]
  ```


## 📦 Estrutura do Projeto


<img width="934" height="1131" alt="Docker Compose Flow" src="https://github.com/user-attachments/assets/9d8bf2e6-0a8a-4c42-a2df-e339f2f50d19" />


##  Teste de Stress

[Gatling Stats - Create User2.pdf](https://github.com/user-attachments/files/21477485/Gatling.Stats.-.Create.User2.pdf)



## 🤝 Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas alterações (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

---

Desenvolvido por Daniel Silva - daniel97silva.ds@gmail.com

