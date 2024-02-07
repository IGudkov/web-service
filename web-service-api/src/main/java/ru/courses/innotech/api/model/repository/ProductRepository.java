package ru.courses.innotech.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ru.courses.innotech.api.model.entity.Product;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {

  Boolean existsByNumber(@NonNull String number);

  Optional<Product> findByNumber(@NonNull String number);

}
