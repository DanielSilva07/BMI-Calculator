package com.danielsilva.imcApplication.controler;

import com.danielsilva.imcApplication.controller.ClienteController;
import com.danielsilva.imcApplication.model.ClienteModel;
import com.danielsilva.imcApplication.repository.ClienteRepository;
import com.danielsilva.imcApplication.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteRepository repository;

    @MockBean
    ClienteService service;

    @BeforeEach
    void setup() {

        // Setup test data
        ClienteModel clienteModel = new ClienteModel();
        clienteModel.setId("1");
        clienteModel.setNome("Test User");
        clienteModel.setAltura(1.75);
        clienteModel.setPeso(70.0);
        clienteModel.setEmail("test@example.com");
        
        // Save the test data
        repository.save(clienteModel);
    }

    @Test
    @DisplayName("deveListarTodosClientes")
    void deveRetornarListaDeClientes() throws Exception {
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/bmi"))
                .andExpect (MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());


    }


}
