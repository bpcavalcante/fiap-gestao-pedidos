package com.fiap.gestao_pedidos.infrastructure.adapters.feign;

import com.fiap.gestao_pedidos.domain.entities.Produto;
import com.fiap.gestao_pedidos.domain.ports.ProdutoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProdutoFeignClientAdapter implements ProdutoRepository {

    private final ProdutoFeignClient produtoFeignClient;

    public ProdutoFeignClientAdapter(ProdutoFeignClient produtoFeignClient) {
        this.produtoFeignClient = produtoFeignClient;
    }

    @Override
    public Optional<Produto> buscarProduto(Long id) {
        return produtoFeignClient.buscarProduto(id);
    }
}
