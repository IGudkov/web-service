package ru.courses.innotech.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ru.courses.innotech.api.model.entity.Agreement;
import ru.courses.innotech.api.model.entity.Product;

import java.util.List;
import java.util.Optional;


public interface AgreementRepository extends JpaRepository<Agreement, Long> {

  List<Agreement> findByProduct(@NonNull Product product);

  Boolean existsByNumber(String number);

  Optional<Agreement> findByProductAndNumber(Product product, String number);

}
