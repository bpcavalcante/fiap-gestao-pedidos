package com.fiap.gestao_pedidos.controller;

import com.fiap.gestao_pedidos.controller.dto.CriarPedidoRequest;
import com.fiap.gestao_pedidos.controller.dto.PedidoCompletoResponse;
import com.fiap.gestao_pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoCompletoResponse> criarPedido(@RequestBody CriarPedidoRequest request) {
        PedidoCompletoResponse pedidoCriado = pedidoService.criarPedido(request);
        return ResponseEntity.ok(pedidoCriado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoCompletoResponse> buscarPedido(@PathVariable Long id) {
        PedidoCompletoResponse response = pedidoService.buscarPedido(id);
        return ResponseEntity.ok(response);
    }
}
