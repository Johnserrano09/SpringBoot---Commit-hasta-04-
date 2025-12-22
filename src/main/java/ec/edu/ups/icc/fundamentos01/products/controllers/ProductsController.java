package ec.edu.ups.icc.fundamentos01.products.controllers;

import ec.edu.ups.icc.fundamentos01.products.entities.Product;
import ec.edu.ups.icc.fundamentos01.products.dtos.*;
import ec.edu.ups.icc.fundamentos01.products.mappers.ProductMapper;
import ec.edu.ups.icc.fundamentos01.products.services.ProductService;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/products")
public class ProductsController {


    private final ProductService service;

    public ProductsController(ProductService service) {
        this.service = service;
    }


    private List<Product> products = new ArrayList<>();
    private int currentId = 1;

    // GET ALL
    @GetMapping
    public List<ProductResponseDto> findAll() {
        return products.stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

@GetMapping("/{id}")
public Object findOne(@PathVariable int id) {

    Product product = products.stream()
            .filter(p -> p.getId() == id)
            .findFirst()
            .orElse(null);

    if (product == null) {
        return Map.of("error", "Product not found");
    }

    return ProductMapper.toResponse(product);
}

    // POST
    @PostMapping
    public ProductResponseDto create(@RequestBody CreateProductDto dto) {
        Product product = ProductMapper.toEntity(
                currentId++, dto.name, dto.price, dto.stock
        );
        products.add(product);
        return ProductMapper.toResponse(product);
    }

    // PUT
    @PutMapping("/{id}")
    public Object update(@PathVariable int id, @RequestBody UpdateProductDto dto) {
        for (Product product : products) {
            if (product.getId() == id) {
                product.setName(dto.name);
                product.setPrice(dto.price);
                product.setStock(dto.stock);
                return ProductMapper.toResponse(product);
            }
        }
        return Map.of("error", "Product not found");
    }

    // PATCH
    @PatchMapping("/{id}")
    public Object partialUpdate(@PathVariable int id, @RequestBody PartialUpdateProductDto dto) {
        for (Product product : products) {
            if (product.getId() == id) {
                if (dto.name != null) product.setName(dto.name);
                if (dto.price != null) product.setPrice(dto.price);
                if (dto.stock != null) product.setStock(dto.stock);
                return ProductMapper.toResponse(product);
            }
        }
        return Map.of("error", "Product not found");
    }

    // DELETE
    @DeleteMapping("/{id}")
    public Object delete(@PathVariable int id) {
        boolean deleted = products.removeIf(p -> p.getId() == id);
        if (!deleted) return Map.of("error", "Product not found");
        return Map.of("message", "Deleted successfully");
    }


 
}
            