package ec.edu.ups.icc.fundamentos01.products.services;

import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.entities.Product;
import ec.edu.ups.icc.fundamentos01.products.mappers.ProductMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    private List<Product> products = new ArrayList<>();
    private int currentId = 1;

    @Override
    public List<ProductResponseDto> findAll() {
        List<ProductResponseDto> result = new ArrayList<>();
        for (Product product : products) {
            result.add(ProductMapper.toResponse(product));
        }
        return result;
    }

    @Override
    public Object findOne(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return ProductMapper.toResponse(product);
            }
        }
        return new Object() { public String error = "Product not found"; };
    }

    @Override
    public ProductResponseDto create(CreateProductDto dto) {
        Product product = ProductMapper.toEntity(
                currentId++, dto.name, dto.price, dto.stock
        );
        products.add(product);
        return ProductMapper.toResponse(product);
    }

    @Override
    public Object update(int id, UpdateProductDto dto) {
        for (Product product : products) {
            if (product.getId() == id) {
                product.setName(dto.name);
                product.setPrice(dto.price);
                product.setStock(dto.stock);
                return ProductMapper.toResponse(product);
            }
        }
        return new Object() { public String error = "Product not found"; };
    }

    @Override
    public Object partialUpdate(int id, PartialUpdateProductDto dto) {
        for (Product product : products) {
            if (product.getId() == id) {
                if (dto.name != null) product.setName(dto.name);
                if (dto.price != null) product.setPrice(dto.price);
                if (dto.stock != null) product.setStock(dto.stock);
                return ProductMapper.toResponse(product);
            }
        }
        return new Object() { public String error = "Product not found"; };
    }

    @Override
    public Object delete(int id) {
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getId() == id) {
                iterator.remove();
                return new Object() { public String message = "Deleted successfully"; };
            }
        }
        return new Object() { public String error = "Product not found"; };
    }
}
