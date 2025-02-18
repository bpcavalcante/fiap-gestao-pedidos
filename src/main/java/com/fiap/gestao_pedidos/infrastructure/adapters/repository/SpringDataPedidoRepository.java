package com.fiap.gestao_pedidos.infrastructure.adapters.repository;

import com.fiap.gestao_pedidos.infrastructure.adapters.repository.entities.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPedidoRepository extends JpaRepository<PedidoEntity, Long> {
}
