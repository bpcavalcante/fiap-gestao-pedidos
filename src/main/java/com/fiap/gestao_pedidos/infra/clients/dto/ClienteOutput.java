package com.fiap.gestao_pedidos.infra.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteOutput {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
}
