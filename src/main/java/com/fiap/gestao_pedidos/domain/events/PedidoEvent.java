package com.fiap.gestao_pedidos.domain.events;

import com.fiap.gestao_pedidos.domain.entities.Cliente;
import com.fiap.gestao_pedidos.domain.entities.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@AllArgsConstructor
public class PedidoEvent {
    private Long id;
    private Cliente cliente;
    private Map<Long, Produto> produtos;
    private LocalDateTime dataCriacao;
    private String status;
}
