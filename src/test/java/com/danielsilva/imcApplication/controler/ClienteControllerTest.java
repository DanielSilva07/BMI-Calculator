package com.danielsilva.imcApplication.controler;

import com.danielsilva.imcApplication.controller.ClienteController;
import com.danielsilva.imcApplication.dtos.ClienteDtoRequest;
import com.danielsilva.imcApplication.model.ClienteModel;
import com.danielsilva.imcApplication.repository.ClienteRepository;
import com.danielsilva.imcApplication.service.ClienteService;
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

        ClienteModel clienteSalvo = new ClienteModel();
        clienteSalvo.setId("1");
        clienteSalvo.setNome(clienteDto.getNome());
        clienteSalvo.setAltura(clienteDto.getAltura());
        clienteSalvo.setPeso(clienteDto.getPeso());
        clienteSalvo.setEmail(clienteDto.getEmail());
        clienteSalvo.imcCalculator();

        // Mock the repository save method
        when(repository.save(any(ClienteModel.class))).thenReturn(clienteSalvo);

        mockMvc.perform(MockMvcRequestBuilders.post("/bmi")
                .contentType("application/json")
                .content("{\"nome\":\"João Silva\",\"altura\":1.75,\"peso\":70.0,\"email\":\"joao.silva@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

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
     org.junit.jupiter.api.Assertions.assertEquals(200, response.getResponse().getStatus());


    }


}
