package com.fiap.gestao_pedidos.infrastructure.adapters.feign;

import com.fiap.gestao_pedidos.domain.entities.Produto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "produto-service", url = "http://localhost:8081/produtos")
public interface ProdutoFeignClient {
    @GetMapping("/{id}")
    Optional<Produto> buscarProduto(@PathVariable Long id);
}