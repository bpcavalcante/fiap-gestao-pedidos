package com.fiap.gestao_pedidos.application.usecases;

import com.fiap.gestao_pedidos.application.dto.response.PedidoCompletoResponse;
import com.fiap.gestao_pedidos.domain.entities.Pedido;
import com.fiap.gestao_pedidos.domain.ports.ClienteRepository;
import com.fiap.gestao_pedidos.domain.ports.PedidoRepository;
import com.fiap.gestao_pedidos.domain.ports.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BuscarPedidoUseCase {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoCompletoResponse executar(Long pedidoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        var cliente = clienteRepository.buscarCliente(pedido.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Map<Long, PedidoCompletoResponse.ProdutoDTO> produtos = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : pedido.getItens().entrySet()) {
            var produto = produtoRepository.buscarProduto(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Produto com ID " + entry.getKey() + " não encontrado"));

            produtos.put(entry.getKey(), new PedidoCompletoResponse.ProdutoDTO(
                    produto.getId(), produto.getNome(), produto.getPreco(), entry.getValue()
            ));
        }

        return new PedidoCompletoResponse(
                pedido.getId(), cliente, produtos, pedido.getDataCriacao(), pedido.getStatus().toString()
        );
    }
}
