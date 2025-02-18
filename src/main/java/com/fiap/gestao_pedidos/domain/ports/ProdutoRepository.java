package com.fiap.gestao_pedidos.domain.ports;


import com.fiap.gestao_pedidos.domain.entities.Produto;

import java.util.Optional;

public interface ProdutoRepository {
    Optional<Produto> buscarProduto(Long id);
}
