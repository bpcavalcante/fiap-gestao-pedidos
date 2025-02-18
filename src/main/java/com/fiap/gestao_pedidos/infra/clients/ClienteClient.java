package com.fiap.gestao_pedidos.infra.clients;

import com.fiap.gestao_pedidos.infra.clients.dto.ClienteOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

@FeignClient(name = "cliente-service", url = "http://localhost:8082/clientes")
public interface ClienteClient {

    @GetMapping("/{id}")
    Optional<ClienteOutput> buscarCliente(@PathVariable Long id);
}
