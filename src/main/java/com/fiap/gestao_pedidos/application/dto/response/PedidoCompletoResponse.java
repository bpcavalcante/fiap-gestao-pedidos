package com.fiap.gestao_pedidos.application.dto.response;

import com.fiap.gestao_pedidos.domain.entities.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@AllArgsConstructor
public class PedidoCompletoResponse {
    private Long id;
    private Cliente cliente;
    private Map<Long, ProdutoDTO> produtos;
    private LocalDateTime dataCriacao;
    private String status;

    @Getter
    @AllArgsConstructor
    public static class ProdutoDTO {
        private Long id;
        private String nome;
        private double preco;
        private int quantidade;
    }
}
