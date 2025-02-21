package com.fiap.gestao_pedidos.application.usecases;

import com.fiap.gestao_pedidos.application.dto.response.PedidoCompletoResponse;
import com.fiap.gestao_pedidos.domain.entities.Cliente;
import com.fiap.gestao_pedidos.domain.entities.Pedido;
import com.fiap.gestao_pedidos.domain.entities.Produto;
import com.fiap.gestao_pedidos.domain.entities.StatusPedido;
import com.fiap.gestao_pedidos.domain.ports.ClienteRepository;
import com.fiap.gestao_pedidos.domain.ports.PedidoRepository;
import com.fiap.gestao_pedidos.domain.ports.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarPedidoUseCaseTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private BuscarPedidoUseCase buscarPedidoUseCase;

    private Pedido pedido;
    private Cliente cliente;
    private Produto produto1;
    private Produto produto2;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        produto1 = new Produto(1L, "Produto 1", 10.0);
        produto2 = new Produto(2L, "Produto 2", 20.0);

        Map<Long, Integer> itensPedido = Map.of(
                1L, 2,
                2L, 1
        );
        pedido = new Pedido(1L, 1L, itensPedido, StatusPedido.PENDENTE, LocalDateTime.now());
    }

    @Test
    void deveBuscarPedidoComSucesso() {
        when(pedidoRepository.buscarPorId(1L)).thenReturn(Optional.of(pedido));
        when(clienteRepository.buscarCliente(1L)).thenReturn(Optional.of(cliente));
        when(produtoRepository.buscarProduto(1L)).thenReturn(Optional.of(produto1));
        when(produtoRepository.buscarProduto(2L)).thenReturn(Optional.of(produto2));

        PedidoCompletoResponse response = buscarPedidoUseCase.executar(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(cliente, response.getCliente());
        assertEquals(2, response.getProdutos().size());
        assertEquals("PENDENTE", response.getStatus());
    }

    @Test
    void deveLancarExcecaoQuandoPedidoNaoEncontrado() {
        when(pedidoRepository.buscarPorId(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            buscarPedidoUseCase.executar(1L);
        });

        assertEquals("Pedido não encontrado", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        when(pedidoRepository.buscarPorId(1L)).thenReturn(Optional.of(pedido));
        when(clienteRepository.buscarCliente(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            buscarPedidoUseCase.executar(1L);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
    }

}
