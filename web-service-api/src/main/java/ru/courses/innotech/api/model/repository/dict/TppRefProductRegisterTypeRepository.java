package ru.courses.innotech.api.model.repository.dict;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ru.courses.innotech.api.model.entity.dict.TppRefProductRegisterType;

import java.util.List;
import java.util.Optional;


public interface TppRefProductRegisterTypeRepository extends JpaRepository<TppRefProductRegisterType, Integer> {

  Optional<TppRefProductRegisterType> findByProductClassCodeAndAccountTypeCode(@NonNull String productClassCode, @NonNull String accountTypeCode);

  Optional<TppRefProductRegisterType> findByProductClassCodeAndValue(@NonNull String productClassCode, @NonNull String value);

}
