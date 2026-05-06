package com.example.tienda.service;

import com.example.tienda.model.Producto;
import com.example.tienda.model.Venta;
import com.example.tienda.repository.ProductoRepository;
import com.example.tienda.repository.VentaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;

    public VentaService(VentaRepository ventaRepository, ProductoRepository productoRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
    }

    public Venta registrar(Long productoId, Integer cantidad) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoId));

        if (cantidad > producto.getStock()) {
            throw new IllegalArgumentException("Stock insuficiente. Stock disponible: " + producto.getStock());
        }

        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);

        Venta venta = new Venta();
        venta.setProductoId(productoId);
        venta.setCantidad(cantidad);
        venta.setTotal(producto.getPrecio() * cantidad);
        venta.setFecha(LocalDateTime.now());

        return ventaRepository.save(venta);
    }

    public List<Venta> listar() {
        return ventaRepository.findAll();
    }
}
