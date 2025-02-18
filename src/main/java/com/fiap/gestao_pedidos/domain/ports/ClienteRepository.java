package com.fiap.gestao_pedidos.domain.ports;

import com.fiap.gestao_pedidos.domain.entities.Cliente;

import java.util.Optional;

public interface ClienteRepository {
    Optional<Cliente> buscarCliente(Long id);
}
