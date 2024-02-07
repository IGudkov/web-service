package ru.courses.innotech.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ru.courses.innotech.api.model.entity.Product;
import ru.courses.innotech.api.model.entity.ProductRegister;
import ru.courses.innotech.api.model.entity.dict.TppRefProductRegisterType;

import java.util.List;


public interface ProductRegisterRepository extends JpaRepository<ProductRegister, Long> {

  List<ProductRegister> findByProduct(@NonNull Product product);

  List<ProductRegister> findByProductAndTppRefProductRegisterType(Product product, TppRefProductRegisterType tppRefProductRegisterType);

}
