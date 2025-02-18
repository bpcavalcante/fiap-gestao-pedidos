package com.fiap.gestao_pedidos.publisher;

import com.fiap.gestao_pedidos.event.PedidoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoPublisher {

    private final StreamBridge streamBridge;

    public void publicarPedido(PedidoEvent pedidoEvent) {
        streamBridge.send("pedidoOutput", pedidoEvent);
    }
}
