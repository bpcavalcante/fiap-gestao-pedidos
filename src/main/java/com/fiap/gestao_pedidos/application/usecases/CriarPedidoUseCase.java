package com.fiap.gestao_pedidos.application.usecases;

import com.fiap.gestao_pedidos.application.dto.request.CriarPedidoRequest;
import com.fiap.gestao_pedidos.application.dto.response.PedidoCompletoResponse;
import com.fiap.gestao_pedidos.domain.entities.Pedido;
import com.fiap.gestao_pedidos.domain.entities.Produto;
import com.fiap.gestao_pedidos.domain.entities.StatusPedido;
import com.fiap.gestao_pedidos.domain.events.PedidoEvent;
import com.fiap.gestao_pedidos.domain.ports.ClienteRepository;
import com.fiap.gestao_pedidos.domain.ports.PedidoPublisher;
import com.fiap.gestao_pedidos.domain.ports.PedidoRepository;
import com.fiap.gestao_pedidos.domain.ports.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CriarPedidoUseCase {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoPublisher pedidoPublisher;

    public PedidoCompletoResponse executar(CriarPedidoRequest request) {
        Long clienteId = request.getClienteId();
        Map<Long, Integer> itensPedido = request.getItens();

        var cliente = clienteRepository.buscarCliente(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Map<Long, PedidoCompletoResponse.ProdutoDTO> produtosDTO = new HashMap<>();
        Map<Long, Produto> produtos = new HashMap<>();

        for (Map.Entry<Long, Integer> entry : itensPedido.entrySet()) {
            Long produtoId = entry.getKey();
            Integer quantidadeItens = entry.getValue();

            Produto produto = produtoRepository.buscarProduto(produtoId)
                    .orElseThrow(() -> new RuntimeException("Produto com ID " + produtoId + " não encontrado"));

            produtosDTO.put(produtoId, new PedidoCompletoResponse.ProdutoDTO(
                    produto.getId(), produto.getNome(), produto.getPreco(), quantidadeItens
            ));

            produtos.put(produtoId, produto);
        }

        Pedido pedido = new Pedido(null, clienteId, itensPedido, StatusPedido.PENDENTE, LocalDateTime.now());
        pedido = pedidoRepository.salvar(pedido);

        PedidoEvent evento = new PedidoEvent(pedido.getId(), cliente, produtos, pedido.getDataCriacao(), pedido.getStatus().name());
        pedidoPublisher.publicarPedido(evento);

        return new PedidoCompletoResponse(pedido.getId(), cliente, produtosDTO, pedido.getDataCriacao(), pedido.getStatus().toString());
    }
}
