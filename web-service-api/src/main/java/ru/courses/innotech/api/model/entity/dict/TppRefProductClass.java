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
@Table(name = "tpp_ref_product_class")
public class TppRefProductClass {

  @Id
  @Column(name = "internal_id", nullable = false)
  private Integer internalId;

  @Column(name = "value", nullable = false)
  private String value;

  @Column(name = "gbl_code", length = 50)
  private String gblCode;

  @Column(name = "gbl_name")
  private String gblName;

  @Column(name = "product_row_code",length = 50)
  private String productRowCode;

  @Column(name = "product_row_name")
  private String productRowName;

  @Column(name = "subclass_code", length = 50)
  private String subclassCode;

  @Column(name = "subclass_name", length = 50)
  private String subclassName;

}
