package com.fiap.gestao_pedidos.controller.dto;

import java.util.Map;

public record CriarPedidoRequest(Long clienteId, Map<Long, Integer> itens) { }

