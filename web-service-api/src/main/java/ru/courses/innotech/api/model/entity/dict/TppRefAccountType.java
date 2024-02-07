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
@Table(name = "tpp_ref_account_type")
public class TppRefAccountType {

  @Id
  @Column(name = "internal_id", nullable = false)
  private Integer internalId;

  @Column(name = "value", nullable = false)
  private String value;

}
