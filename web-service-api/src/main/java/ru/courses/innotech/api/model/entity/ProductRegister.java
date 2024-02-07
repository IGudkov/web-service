package ru.courses.innotech.api.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.courses.innotech.api.model.entity.dict.TppRefProductRegisterType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tpp_product_register")
public class ProductRegister {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;


  @ManyToOne
  @JoinColumn(name = "type")
  private TppRefProductRegisterType tppRefProductRegisterType;

  @Column(name = "currency_code")
  private String currencyCode;

  @Column(name = "state")
  private String state;

  @Column(name = "account_number")
  private String accountNumber;


}
