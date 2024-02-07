package ru.courses.innotech.api.model.entity.dict;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tpp_ref_product_register_type")
public class TppRefProductRegisterType {

  @Id
  @Column(name = "internal_id", nullable = false)
  private Integer internalId;

  @Column(name = "value", nullable = false)
  private String value;

  @Column(name = "register_type_name")
  private String registerTypeName;

  @Column(name = "product_class_code", length = 50)
  private String productClassCode;

  @Column(name = "account_type_code",length = 50)
  private String accountTypeCode;

}
