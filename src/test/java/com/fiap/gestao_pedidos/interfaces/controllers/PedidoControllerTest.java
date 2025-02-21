package com.fiap.gestao_pedidos.interfaces.controllers;

import com.fiap.gestao_pedidos.application.usecases.CriarPedidoUseCase;
import com.fiap.gestao_pedidos.application.usecases.BuscarPedidoUseCase;
import com.fiap.gestao_pedidos.application.dto.response.PedidoCompletoResponse;
import com.fiap.gestao_pedidos.application.dto.request.CriarPedidoRequest;
import com.fiap.gestao_pedidos.application.dto.response.PedidoCompletoResponse.ProdutoDTO;
import com.fiap.gestao_pedidos.domain.entities.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoControllerTest {

    @Mock
    private CriarPedidoUseCase criarPedidoUseCase;

    @Mock
    private BuscarPedidoUseCase buscarPedidoUseCase;

    @InjectMocks
    private PedidoController pedidoController;

    private CriarPedidoRequest criarPedidoRequest;
    private PedidoCompletoResponse pedidoCompletoResponse;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente();
        Map<Long, Integer> itens = Map.of(1L, 2, 2L, 1);
        criarPedidoRequest = new CriarPedidoRequest(1L, itens);

        Map<Long, ProdutoDTO> produtos = Map.of(
                1L, new ProdutoDTO(1L, "Produto 1", 10.0, 2),
                2L, new ProdutoDTO(2L, "Produto 2", 20.0, 1)
        );
        pedidoCompletoResponse = new PedidoCompletoResponse(1L, cliente, produtos, LocalDateTime.now(), "PROCESSANDO");
    }

    @Test
    void deveCriarPedidoERetornarResponseEntity() {
        when(criarPedidoUseCase.executar(criarPedidoRequest)).thenReturn(pedidoCompletoResponse);

        ResponseEntity<PedidoCompletoResponse> response = pedidoController.criarPedido(criarPedidoRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(pedidoCompletoResponse, response.getBody());
        verify(criarPedidoUseCase, times(1)).executar(criarPedidoRequest);
    }

    @Test
    void deveBuscarPedidoERetornarResponseEntity() {
        Long pedidoId = 1L;
        when(buscarPedidoUseCase.executar(pedidoId)).thenReturn(pedidoCompletoResponse);

        ResponseEntity<PedidoCompletoResponse> response = pedidoController.buscarPedido(pedidoId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(pedidoCompletoResponse, response.getBody());
        verify(buscarPedidoUseCase, times(1)).executar(pedidoId);
    }
}
