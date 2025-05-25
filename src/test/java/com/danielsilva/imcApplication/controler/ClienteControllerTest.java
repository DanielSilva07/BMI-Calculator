package com.danielsilva.imcApplication.controler;

import com.danielsilva.imcApplication.controller.ClienteController;
import com.danielsilva.imcApplication.dtos.ClienteDtoRequest;
import com.danielsilva.imcApplication.model.ClienteModel;
import com.danielsilva.imcApplication.repository.ClienteRepository;
import com.danielsilva.imcApplication.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
        // Arrange
        ClienteDtoRequest clienteDto = new ClienteDtoRequest(
                "João Silva",
                1.75,
                70.0,
                "joao.silva@example.com"
        );

        ClienteModel clienteSalvo = ClienteModel.builder()
                .id("1")
                .nome(clienteDto.getNome())
                .altura(clienteDto.getAltura())
                .peso(clienteDto.getPeso())
                .email(clienteDto.getEmail())
                .build();
        clienteSalvo.imcCalculator();

        // Mock the repository save method
        when(repository.save(any(ClienteModel.class))).thenReturn(clienteSalvo);

        mockMvc.perform(MockMvcRequestBuilders.post("/bmi")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(clienteDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        // Assert

        assertEquals("João Silva", clienteSalvo.getNome());
        assertEquals(1.75, clienteSalvo.getAltura(), 0.001);
        assertEquals(70.0, clienteSalvo.getPeso(), 0.001);
        assertEquals("joao.silva@example.com", clienteSalvo.getEmail());
    }

    @Test
    @DisplayName("deveListarTodosClientes")
    void deveRetornarListaDeClientes() throws Exception {
        // Act & Assert
     MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/bmi"))
                .andExpect (MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
     response.getResponse().getContentAsString();
     assertEquals(200, response.getResponse().getStatus());


    }


}
