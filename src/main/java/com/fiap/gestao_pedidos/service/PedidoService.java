package com.fiap.gestao_pedidos.service;

import com.fiap.gestao_pedidos.controller.dto.CriarPedidoRequest;
import com.fiap.gestao_pedidos.controller.dto.PedidoCompletoResponse;
import com.fiap.gestao_pedidos.model.Pedido;
import com.fiap.gestao_pedidos.model.StatusPedido;
import com.fiap.gestao_pedidos.infra.clients.ClienteClient;
import com.fiap.gestao_pedidos.infra.clients.ProdutoClient;
import com.fiap.gestao_pedidos.infra.clients.dto.ClienteOutput;
import com.fiap.gestao_pedidos.infra.clients.dto.Produto;
import com.fiap.gestao_pedidos.infra.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteClient clienteClient;
    private final ProdutoClient produtoClient;

    @Transactional
    public PedidoCompletoResponse criarPedido(CriarPedidoRequest request) {
        // 1️⃣ Verifica se o cliente existe no serviço externo
        ClienteOutput cliente = clienteClient.buscarCliente(request.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // 2️⃣ Verifica se todos os produtos existem no serviço externo
        Map<Long, PedidoCompletoResponse.ProdutoDTO> produtos = new HashMap<>();
        for (Long produtoId : request.itens().keySet()) {
            Produto produto = produtoClient.buscarProduto(produtoId)
                    .orElseThrow(() -> new RuntimeException("Produto com ID " + produtoId + " não encontrado"));
            // Adiciona a quantidade de itens no campo quantidadeItens
            Integer quantidadeItens = request.itens().get(produtoId);
            produtos.put(produtoId, new PedidoCompletoResponse.ProdutoDTO(produto.getId(), produto.getNome(), produto.getPreco(), quantidadeItens));
        }

        // 3️⃣ Cria e salva o pedido
        Pedido pedido = Pedido.builder()
                .clienteId(request.clienteId())
                .itens(request.itens())
                .status(StatusPedido.PENDENTE)
                .dataCriacao(LocalDateTime.now())
                .build();

        pedido = pedidoRepository.save(pedido);

        // 4️⃣ Retorna a resposta com os dados completos
        return new PedidoCompletoResponse(pedido.getId(), cliente, produtos, pedido.getDataCriacao(), pedido.getStatus().toString());
    }
}