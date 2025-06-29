package com.danielsilva.imcApplication.controler;
import com.danielsilva.imcApplication.controller.ClienteController;
import com.danielsilva.imcApplication.dtos.ClienteDtoResponse;
import com.danielsilva.imcApplication.infra.repository.ClienteRepository;
import com.danielsilva.imcApplication.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.math.BigDecimal;
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
    public void deveRetornarListaDeClientes() throws Exception {
        ClienteDtoResponse expectedResponse = new ClienteDtoResponse(
                1L,
                "João Silva",
                "joao.silva@example.com",
                new BigDecimal("1.75"),
                new BigDecimal("70.0"),
                new BigDecimal("24.4")
        );

        List<ClienteDtoResponse> expectedList = List.of(expectedResponse);
        when(service.clientList()).thenReturn((expectedList));

        mockMvc.perform(MockMvcRequestBuilders.get("/bmi"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedList)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldNotSaveIsNameIsBlank() throws Exception {
        var body = """
                {
                    "nome": "",
                    "altura": 1.75,
                    "peso": 70.0,
                    "email": "joao.silva@example.com"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/bmi")
                        .contentType("application/json")
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    @Test
    void shouldNotSaveIsNameIsNull() throws Exception {
        var body = """
                {
                    "nome": null,
                    "altura": 1.75,
                    "peso": 70.0,
                    "email": "joao.silva@example.com"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/bmi")
                        .contentType("application/json")
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void shouldDeleteByIdWhenIdExists() throws Exception {

        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/bmi/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
