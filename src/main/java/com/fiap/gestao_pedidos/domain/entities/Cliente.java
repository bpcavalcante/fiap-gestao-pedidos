package com.fiap.gestao_pedidos.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Cliente {
    private Long id;
    private String nome;
    private String email;
}
