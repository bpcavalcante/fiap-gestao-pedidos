package com.fiap.gestao_pedidos.infrastructure.adapters.messaging;

import com.fiap.gestao_pedidos.domain.events.PedidoEvent;
import com.fiap.gestao_pedidos.domain.ports.PedidoPublisher;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class PedidoPublisherImpl implements PedidoPublisher {

    private final StreamBridge streamBridge;

    public PedidoPublisherImpl(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void publicarPedido(PedidoEvent pedidoEvent) {
        streamBridge.send("pedidoOutput", pedidoEvent);
    }
}
