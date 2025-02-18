package com.fiap.gestao_pedidos.domain.ports;

import com.fiap.gestao_pedidos.domain.events.PedidoEvent;

public interface PedidoPublisher {
    void publicarPedido(PedidoEvent pedidoEvent);
}
