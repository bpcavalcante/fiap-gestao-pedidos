package com.fiap.gestao_pedidos.domain.ports;

import com.fiap.gestao_pedidos.domain.entities.Pedido;

import java.util.Optional;

public interface PedidoRepository {
    Pedido salvar(Pedido pedido);
    Optional<Pedido> buscarPorId(Long id);
}
