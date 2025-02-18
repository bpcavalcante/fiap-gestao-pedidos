package com.fiap.gestao_pedidos.infrastructure.adapters.feign;

import com.fiap.gestao_pedidos.domain.entities.Cliente;
import com.fiap.gestao_pedidos.domain.ports.ClienteRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClienteFeignClientAdapter implements ClienteRepository {

    private final ClienteFeignClient clienteFeignClient;

    public ClienteFeignClientAdapter(ClienteFeignClient clienteFeignClient) {
        this.clienteFeignClient = clienteFeignClient;
    }

    @Override
    public Optional<Cliente> buscarCliente(Long id) {
        return clienteFeignClient.buscarCliente(id);
    }
}