package com.fiap.gestao_pedidos.interfaces.controllers;

import com.fiap.gestao_pedidos.application.usecases.CriarPedidoUseCase;
import com.fiap.gestao_pedidos.application.usecases.BuscarPedidoUseCase;
import com.fiap.gestao_pedidos.application.dto.response.PedidoCompletoResponse;
import com.fiap.gestao_pedidos.application.dto.request.CriarPedidoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final CriarPedidoUseCase criarPedidoUseCase;
    private final BuscarPedidoUseCase buscarPedidoUseCase;

    @PostMapping
    public ResponseEntity<PedidoCompletoResponse> criarPedido(@RequestBody CriarPedidoRequest request) {
        return ResponseEntity.ok(criarPedidoUseCase.executar(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoCompletoResponse> buscarPedido(@PathVariable Long id) {
        return ResponseEntity.ok(buscarPedidoUseCase.executar(id));
    }

}
