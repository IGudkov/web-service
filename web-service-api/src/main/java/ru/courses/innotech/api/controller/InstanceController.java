package ru.courses.innotech.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.courses.innotech.specs.exceptions.ServiceException;
import ru.courses.innotech.api.service.InstanceService;
import ru.courses.innotech.specs.model.instance.create.InstanceCreateRequest;
import ru.courses.innotech.specs.model.instance.create.InstanceCreateResponse;
import ru.courses.innotech.specs.resource.InstanceResource;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InstanceController implements InstanceResource {

  private final InstanceService instanceService;

  public ResponseEntity<InstanceCreateResponse> create(InstanceCreateRequest instanceCreateRequest) throws ServiceException {
    return instanceService.create(instanceCreateRequest);
  }
}
