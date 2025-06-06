package com.danielsilva.imcApplication.service;
import com.danielsilva.imcApplication.fixtures.Fixtures;
import com.danielsilva.imcApplication.dtos.ClienteDtoResponse;
import com.danielsilva.imcApplication.infra.kafka.MessageProducer;
import com.danielsilva.imcApplication.model.ClienteModel;
import com.danielsilva.imcApplication.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private MessageProducer messageProducer;

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService service;

    @Test
    public void deveSalvarUmNovoCliente() {
        // Arrange
        var clienteDto = Fixtures.buildClienteDtoRequest();
        var clienteModel = Fixtures.buildClienteDtoResponse();

        when(repository.save(any(ClienteModel.class))).thenReturn(clienteModel);

        ClienteModel resultado = service.save(clienteDto);

        // Assert
        assertNotNull(resultado);
        assertEquals(clienteDto.getNome(), resultado.getNome());
        assertEquals(clienteDto.getAltura(), resultado.getAltura(), 0.001);
        assertEquals(clienteDto.getPeso(), resultado.getPeso(), 0.001);
        assertEquals(clienteDto.getEmail(), resultado.getEmail());
        assertEquals(clienteModel.getImc(), resultado.getImc());

        // Verify interactions
        verify(repository, times(1)).save(any(ClienteModel.class));
        verify(messageProducer, times(1)).sendMessage("imc", resultado.getImc().toString());
    }


    @Test
    public void getAll_DeveRetornarListaVazia_QuandoNaoHouverClientes() {
        // Arrange
        when(repository.findAll()).thenReturn(Collections.emptyList());
        
        // Act
        List<ClienteDtoResponse> clientes = service.getAll();
        
        // Assert
        assertEquals(0, clientes.size());
        verify(repository, Mockito.times(1)).findAll();
    }
    
    @Test
    public void getAll_DeveRetornarListaDeClientes_QuandoExistiremClientes() {
        // Arrange
        ClienteModel cliente1 = new ClienteModel();
        cliente1.setId("1");
        cliente1.setNome("Daniel");
        cliente1.setAltura(1.80);
        cliente1.setPeso(80.0);
        cliente1.setEmail("daniel@example.com");
        
        ClienteModel cliente2 = new ClienteModel();
        cliente2.setId("2");
        cliente2.setNome("João");
        cliente2.setAltura(1.75);
        cliente2.setPeso(70.0);
        cliente2.setEmail("joao@example.com");
        
        List<ClienteModel> clientesMock = Arrays.asList(cliente1, cliente2);
        
        when(repository.findAll()).thenReturn(clientesMock);
        
        // Act
        List<ClienteDtoResponse> resultado = service.getAll();
        
        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Daniel", resultado.get(0).getNome());
        assertEquals("João", resultado.get(1).getNome());
        verify(repository, Mockito.times(1)).findAll();
    }

}
