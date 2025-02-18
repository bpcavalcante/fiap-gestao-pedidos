package com.fiap.gestao_pedidos.infrastructure.adapters.feign;

import com.fiap.gestao_pedidos.domain.entities.Cliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "cliente-service", url = "http://localhost:8082/clientes")
public interface ClienteFeignClient {
    @GetMapping("/{id}")
    Optional<Cliente> buscarCliente(@PathVariable Long id);
}