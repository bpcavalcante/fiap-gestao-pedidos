package com.fiap.gestao_pedidos.infrastructure.adapters.repository;

import com.fiap.gestao_pedidos.domain.entities.Pedido;
import com.fiap.gestao_pedidos.domain.entities.StatusPedido;
import com.fiap.gestao_pedidos.infrastructure.adapters.repository.entities.PedidoEntity;
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
class JpaPedidoRepositoryTest {

    @Mock
    private SpringDataPedidoRepository springDataPedidoRepository;

    @InjectMocks
    private JpaPedidoRepository jpaPedidoRepository;

    private Pedido pedido;
    private PedidoEntity pedidoEntity;

    @BeforeEach
    void setUp() {
        Map<Long, Integer> itensPedido = Map.of(1L, 2, 2L, 1);
        pedido = new Pedido(1L, 1L, itensPedido, StatusPedido.PENDENTE, LocalDateTime.now());
        pedidoEntity = PedidoEntity.fromDomain(pedido);
    }

    @Test
    void deveSalvarPedidoComSucesso() {
        when(springDataPedidoRepository.save(any(PedidoEntity.class))).thenReturn(pedidoEntity);

        Pedido resultado = jpaPedidoRepository.salvar(pedido);

        assertNotNull(resultado);
        assertEquals(pedido.getId(), resultado.getId());
        assertEquals(pedido.getClienteId(), resultado.getClienteId());
        assertEquals(pedido.getItens(), resultado.getItens());
        assertEquals(pedido.getStatus(), resultado.getStatus());
    }

    @Test
    void deveBuscarPedidoPorIdComSucesso() {
        when(springDataPedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoEntity));

        Optional<Pedido> resultado = jpaPedidoRepository.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(pedido.getId(), resultado.get().getId());
    }

    @Test
    void deveRetornarVazioQuandoPedidoNaoExistir() {
        when(springDataPedidoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Pedido> resultado = jpaPedidoRepository.buscarPorId(1L);

        assertFalse(resultado.isPresent());
    }
}
