package com.fiap.gestao_pedidos.event;

import com.fiap.gestao_pedidos.infra.clients.dto.ClienteOutput;
import com.fiap.gestao_pedidos.infra.clients.dto.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoEvent {
    private Long id;
    private ClienteOutput cliente;
    private Map<Long, Produto> produtos;
    private LocalDateTime dataCriacao;
    private String status;
}
