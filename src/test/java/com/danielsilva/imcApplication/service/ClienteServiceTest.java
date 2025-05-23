package com.danielsilva.imcApplication.service;
import com.danielsilva.imcApplication.dtos.ClienteDtoRequest;
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
    private ClienteRepository repository;

    @Mock
    private NotificationRabbitService notificationRabbitService;

    @InjectMocks
    private ClienteService service;

    @Test
    public void deveSalvarUmNovoCliente() {
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

        // Mock the notification service to do nothing


        // Act
        ClienteModel resultado = service.save(clienteDto);

        // Assert
        assertNotNull(resultado);
        assertEquals("1", resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals(1.75, resultado.getAltura(), 0.001);
        assertEquals(70.0, resultado.getPeso(), 0.001);
        assertEquals("joao.silva@example.com", resultado.getEmail());
        assertNotNull(resultado.getImc());

        // Verify interactions
        verify(repository, times(1)).save(any(ClienteModel.class));

    }


    @Test
    public void getAll_DeveRetornarListaVazia_QuandoNaoHouverClientes() {
        // Arrange
        when(repository.findAll()).thenReturn(Collections.emptyList());
        
        // Act
        List<ClienteModel> clientes = service.getAll();
        
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
        List<ClienteModel> resultado = service.getAll();
        
        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Daniel", resultado.get(0).getNome());
        assertEquals("João", resultado.get(1).getNome());
        verify(repository, Mockito.times(1)).findAll();
    }

}
