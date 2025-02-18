package com.fiap.gestao_pedidos.infrastructure.adapters.repository;

import com.fiap.gestao_pedidos.domain.entities.Pedido;
import com.fiap.gestao_pedidos.domain.ports.PedidoRepository;
import com.fiap.gestao_pedidos.infrastructure.adapters.repository.entities.PedidoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaPedidoRepository implements PedidoRepository {

    private final SpringDataPedidoRepository springDataPedidoRepository;

    @Override
    public Pedido salvar(Pedido pedido) {
        PedidoEntity pedidoEntity = PedidoEntity.fromDomain(pedido);
        return springDataPedidoRepository.save(pedidoEntity).toDomain();
    }

    @Override
    public Optional<Pedido> buscarPorId(Long id) {
        return springDataPedidoRepository.findById(id).map(PedidoEntity::toDomain);
    }
}
