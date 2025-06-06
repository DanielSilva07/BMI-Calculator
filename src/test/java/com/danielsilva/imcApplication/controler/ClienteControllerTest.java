package com.danielsilva.imcApplication.controler;

import com.danielsilva.imcApplication.controller.ClienteController;
import com.danielsilva.imcApplication.dtos.ClienteDtoResponse;
import com.danielsilva.imcApplication.repository.ClienteRepository;
import com.danielsilva.imcApplication.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import static org.mockito.Mockito.when;


@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteRepository repository;

    @MockBean
    ClienteService service;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @DisplayName("deveSalvarUmNovoCliente")
    void deveSalvarUmNovoCliente() throws Exception {
        var body = """
                {
                    "nome": "João Silva",
                    "altura": 1.75,
                    "peso": 70.0,
                    "email": "joao.silva@example.com"
                }
                """;


        mockMvc.perform(MockMvcRequestBuilders.post("/bmi")
                .contentType("application/json")
                .content(body))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @DisplayName("deveListarTodosClientes")
    public void deveRetornarListaDeClientes() throws Exception {
        // Arrange
        ClienteDtoResponse expectedResponse = new ClienteDtoResponse(
                "1",
                "João Silva",
                "joao.silva@example.com",
                1.75,
                70.0,
                24.4
        );

        // Mock the service response
        List<ClienteDtoResponse> expectedList = List.of(expectedResponse);
        when(service.getAll()).thenReturn((expectedList));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/bmi"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedList)))
                .andDo(MockMvcResultHandlers.print());
    }
}
