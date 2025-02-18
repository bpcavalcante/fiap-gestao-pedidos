package com.fiap.gestao_pedidos.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CriarPedidoRequest {
    private Long clienteId;
    private Map<Long, Integer> itens;
}
