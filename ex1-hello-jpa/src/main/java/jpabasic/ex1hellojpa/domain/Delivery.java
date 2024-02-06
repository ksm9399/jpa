package jpabasic.ex1hellojpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Delivery {

  @Id @GeneratedValue
  @Column(name = "DELIVERT_ID")
  private Long id;

  private DeliveryStatus status;

  @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
  private Order order;

  @Embedded
  private Address address;
}
