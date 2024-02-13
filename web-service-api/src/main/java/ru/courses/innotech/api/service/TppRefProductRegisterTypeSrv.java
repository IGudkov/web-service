package ru.courses.innotech.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.courses.innotech.api.model.entity.dict.TppRefProductRegisterType;
import ru.courses.innotech.api.model.repository.dict.TppRefProductRegisterTypeRepository;

@Service
@RequiredArgsConstructor
public class TppRefProductRegisterTypeSrv {

  private final TppRefProductRegisterTypeRepository tppRefProductRegisterTypeRepository;

  TppRefProductRegisterType getTppRefProductRegisterType(String productClassCode, String value) {
    return tppRefProductRegisterTypeRepository.findByProductClassCodeAndValue(productClassCode, value).orElse(null);
  }

}
