package ru.courses.innotech.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.courses.innotech.api.model.entity.Product;
import ru.courses.innotech.api.model.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductSrv {

  private final ProductRepository productRepository;

  Product getProductById(Long id) {
    return productRepository.findById(id).orElse(null);
  }

}
