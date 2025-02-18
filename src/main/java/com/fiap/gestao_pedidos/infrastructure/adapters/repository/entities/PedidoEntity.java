package com.fiap.gestao_pedidos.infrastructure.adapters.repository.entities;

import com.fiap.gestao_pedidos.domain.entities.Pedido;
import com.fiap.gestao_pedidos.domain.entities.StatusPedido;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long clienteId;

    @ElementCollection
    @CollectionTable(name = "pedido_itens", joinColumns = @JoinColumn(name = "pedido_id"))
    @MapKeyColumn(name = "produto_id")
    @Column(name = "quantidade")
    private Map<Long, Integer> itens;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status;

    // 🔄 Conversão entre Entity e Domain
    public static PedidoEntity fromDomain(Pedido pedido) {
        return PedidoEntity.builder()
                .id(pedido.getId())
                .clienteId(pedido.getClienteId())
                .itens(pedido.getItens())
                .status(pedido.getStatus())
                .dataCriacao(pedido.getDataCriacao())
                .build();
    }

    public Pedido toDomain() {
        return new Pedido(id, clienteId, itens, status, dataCriacao);
    }
}
