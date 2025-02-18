package com.fiap.gestao_pedidos.infra.clients;

import com.fiap.gestao_pedidos.infra.clients.dto.Produto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

@FeignClient(name = "produto-service", url = "http://localhost:8081/produtos")
public interface ProdutoClient {

    @GetMapping("/{id}")
    Optional<Produto> buscarProduto(@PathVariable Long id);
}
