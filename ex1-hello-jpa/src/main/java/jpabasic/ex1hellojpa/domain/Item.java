package jpabasic.ex1hellojpa.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
// @Inheritance(strategy = InheritanceType.JOINED) // album, book, movie 각각의 테이블 생성
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // 아이템 테이블안에 album, book, movie 컬럼 생성 - 간단하고 확장이 없다고 예상되면 사용
@DiscriminatorColumn
public abstract class Item {

  @Id @GeneratedValue
  @Column(name = "ITEM_ID")
  private Long id;

  private String name;
  private int price;
  private int stockQuantity;

  @OneToMany(mappedBy = "item")
  private List<OrderItem> orderItems = new ArrayList<>();

  @ManyToMany(mappedBy = "items")
  private List<Category> categories = new ArrayList<>();
}
