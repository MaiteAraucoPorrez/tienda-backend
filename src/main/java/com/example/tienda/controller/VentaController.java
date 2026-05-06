package com.example.tienda.controller;

import com.example.tienda.model.Venta;
import com.example.tienda.service.VentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Map<String, Object> body) {
        try {
            Long productoId = Long.valueOf(body.get("productoId").toString());
            Integer cantidad = Integer.valueOf(body.get("cantidad").toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.registrar(productoId, cantidad));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public List<Venta> listar() {
        return ventaService.listar();
    }
}
