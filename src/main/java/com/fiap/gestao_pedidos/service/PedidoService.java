package com.fiap.gestao_pedidos.service;

import com.fiap.gestao_pedidos.controller.dto.CriarPedidoRequest;
import com.fiap.gestao_pedidos.controller.dto.PedidoCompletoResponse;
import com.fiap.gestao_pedidos.event.PedidoEvent;
import com.fiap.gestao_pedidos.model.Pedido;
import com.fiap.gestao_pedidos.model.StatusPedido;
import com.fiap.gestao_pedidos.infra.clients.ClienteClient;
import com.fiap.gestao_pedidos.infra.clients.ProdutoClient;
import com.fiap.gestao_pedidos.infra.clients.dto.ClienteOutput;
import com.fiap.gestao_pedidos.infra.clients.dto.Produto;
import com.fiap.gestao_pedidos.infra.repository.PedidoRepository;
import com.fiap.gestao_pedidos.publisher.PedidoPublisher;
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
    private final PedidoPublisher pedidoPublisher;

    @Transactional
    public PedidoCompletoResponse criarPedido(CriarPedidoRequest request) {
        // Buscar dados do cliente
        ClienteOutput cliente = clienteClient.buscarCliente(request.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Buscar dados dos produtos e montar o Map<Long, ProdutoDTO>
        Map<Long, PedidoCompletoResponse.ProdutoDTO> produtosDTO = new HashMap<>();
        Map<Long, Produto> produtos = new HashMap<>(); // Map para o PedidoEvent

        for (Map.Entry<Long, Integer> entry : request.itens().entrySet()) {
            Long produtoId = entry.getKey();
            Integer quantidadeItens = entry.getValue();

            Produto produto = produtoClient.buscarProduto(produtoId)
                    .orElseThrow(() -> new RuntimeException("Produto com ID " + produtoId + " não encontrado"));

            // Adicionar ao Map de ProdutoDTO
            produtosDTO.put(produtoId, new PedidoCompletoResponse.ProdutoDTO(
                    produto.getId(),
                    produto.getNome(),
                    produto.getPreco(),
                    quantidadeItens
            ));

            // Adicionar ao Map de Produto (para o PedidoEvent)
            produtos.put(produtoId, produto);
        }

        // Criar e salvar pedido
        Pedido pedido = Pedido.builder()
                .clienteId(request.clienteId())
                .itens(request.itens())
                .status(StatusPedido.PENDENTE)
                .dataCriacao(LocalDateTime.now())
                .build();

        pedido = pedidoRepository.save(pedido);

        // Publicar evento para o RabbitMQ
        PedidoEvent pedidoEvent = new PedidoEvent(
                pedido.getId(),
                cliente,
                produtos, // Usando o Map<Long, Produto>
                pedido.getDataCriacao(),
                pedido.getStatus().name()
        );
        pedidoPublisher.publicarPedido(pedidoEvent);

        // Retornar o PedidoCompletoResponse
        return new PedidoCompletoResponse(
                pedido.getId(),
                cliente,
                produtosDTO, // Usando o Map<Long, ProdutoDTO>
                pedido.getDataCriacao(),
                pedido.getStatus().toString()
        );
    }

    @Transactional(readOnly = true)
    public PedidoCompletoResponse buscarPedido(Long pedidoId) {
        // 1️⃣ Busca o pedido no banco de dados
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        // 2️⃣ Busca os dados do cliente
        ClienteOutput cliente = clienteClient.buscarCliente(pedido.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // 3️⃣ Busca os dados dos produtos e monta a resposta
        Map<Long, PedidoCompletoResponse.ProdutoDTO> produtos = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : pedido.getItens().entrySet()) {
            Long produtoId = entry.getKey();
            Integer quantidadeItens = entry.getValue();

            Produto produto = produtoClient.buscarProduto(produtoId)
                    .orElseThrow(() -> new RuntimeException("Produto com ID " + produtoId + " não encontrado"));

            produtos.put(produtoId, new PedidoCompletoResponse.ProdutoDTO(produto.getId(), produto.getNome(), produto.getPreco(), quantidadeItens));
        }

        // 4️⃣ Retorna os dados completos do pedido
        return new PedidoCompletoResponse(pedido.getId(), cliente, produtos, pedido.getDataCriacao(), pedido.getStatus().toString());
    }

}