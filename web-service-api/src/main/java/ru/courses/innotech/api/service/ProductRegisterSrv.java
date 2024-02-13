package ru.courses.innotech.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.courses.innotech.api.model.entity.Product;
import ru.courses.innotech.api.model.entity.ProductRegister;
import ru.courses.innotech.api.model.entity.dict.TppRefProductRegisterType;
import ru.courses.innotech.api.model.repository.ProductRegisterRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductRegisterSrv {

  private final ProductRegisterRepository productRegisterRepository;

  List<ProductRegister> getListProductRegister(Product product, TppRefProductRegisterType tppRefProductRegisterType) {
    return productRegisterRepository.findByProductAndTppRefProductRegisterType(product, tppRefProductRegisterType);
  }

  void save(ProductRegister productRegister) {
    productRegisterRepository.save(productRegister);
  }

}
