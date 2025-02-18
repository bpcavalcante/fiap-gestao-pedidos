package com.fiap.gestao_pedidos.controller.dto;

import com.fiap.gestao_pedidos.infra.clients.dto.ClienteOutput;
import java.time.LocalDateTime;
import java.util.Map;

public class PedidoCompletoResponse {

    private Long id;
    private ClienteOutput cliente;
    private Map<Long, ProdutoDTO> produtos;
    private LocalDateTime dataCriacao;
    private String status;

    public PedidoCompletoResponse(Long id, ClienteOutput cliente, Map<Long, ProdutoDTO> produtos, LocalDateTime dataCriacao, String status) {
        this.id = id;
        this.cliente = cliente;
        this.produtos = produtos;
        this.dataCriacao = dataCriacao;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClienteOutput getCliente() {
        return cliente;
    }

    public void setCliente(ClienteOutput cliente) {
        this.cliente = cliente;
    }

    public Map<Long, ProdutoDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(Map<Long, ProdutoDTO> produtos) {
        this.produtos = produtos;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class ProdutoDTO {
        private Long id;
        private String nome;
        private Double preco;
        private Integer quantidadeItens;

        public ProdutoDTO(Long id, String nome, Double preco, Integer quantidadeItens) {
            this.id = id;
            this.nome = nome;
            this.preco = preco;
            this.quantidadeItens = quantidadeItens;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public Double getPreco() {
            return preco;
        }

        public void setPreco(Double preco) {
            this.preco = preco;
        }

        public Integer getQuantidadeItens() {
            return quantidadeItens;
        }

        public void setQuantidadeItens(Integer quantidadeItens) {
            this.quantidadeItens = quantidadeItens;
        }
    }
}