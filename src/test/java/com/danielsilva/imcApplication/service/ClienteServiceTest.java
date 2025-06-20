package com.danielsilva.imcApplication.service;

import com.danielsilva.imcApplication.fixtures.Fixtures;
import com.danielsilva.imcApplication.dtos.ClienteDtoResponse;
import com.danielsilva.imcApplication.domain.ClienteModel;
import com.danielsilva.imcApplication.infra.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;
    
    @Mock
    private OutboxService outboxService;
    
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private ClienteService service;


    @Test
    public void deveSalvarUmNovoCliente() {
        var clienteDto = Fixtures.buildClienteDtoRequest();
        var clienteModel = Fixtures.buildClienteDtoResponse();

        when(repository.save(any(ClienteModel.class))).thenReturn(clienteModel);

        ClienteModel resultado = service.save(clienteDto);

        assertNotNull(resultado);
        assertEquals(clienteDto.getNome(), resultado.getNome());
        assertEquals(clienteDto.getAltura(), resultado.getAltura());
        assertEquals(clienteDto.getPeso(), resultado.getPeso());
        assertEquals(clienteDto.getEmail(), resultado.getEmail());
        assertEquals(clienteModel.getImc(), resultado.getImc());

        verify(repository, times(1)).save(any(ClienteModel.class));
        verify(outboxService, times(1)).saveToOutbox(any(ClienteModel.class), anyString());
    }


    @Test
    public void clientList_DeveRetornarListaVazia_QuandoNaoHouverClientes() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<ClienteDtoResponse> clientes = service.clientList();

        assertEquals(0, clientes.size());
        verify(repository, Mockito.times(1)).findAll();
    }
    
    @Test
    public void clientList_DeveRetornarListaDeClientes_QuandoExistiremClientes() {
        ClienteModel cliente1 = new ClienteModel();
        cliente1.setId(1L);
        cliente1.setNome("Daniel");
        cliente1.setAltura(new BigDecimal("1.80"));
        cliente1.setPeso(new BigDecimal("80.0"));
        cliente1.setEmail("daniel@example.com");
        
        ClienteModel cliente2 = new ClienteModel();
        cliente2.setId(2L);
        cliente2.setNome("João");
        cliente2.setAltura(new BigDecimal("1.70"));
        cliente2.setPeso(new BigDecimal("70.0"));
        cliente2.setEmail("joao@example.com");
        
        List<ClienteModel> clientesMock = Arrays.asList(cliente1, cliente2);
        
        when(repository.findAll()).thenReturn(clientesMock);

        List<ClienteDtoResponse> resultado = service.clientList();

        assertEquals(2, resultado.size());
        assertEquals("Daniel", resultado.get(0).getNome());
        assertEquals("João", resultado.get(1).getNome());
        verify(repository, Mockito.times(1)).findAll();
    }

}
