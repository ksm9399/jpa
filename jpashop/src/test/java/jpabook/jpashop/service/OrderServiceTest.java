package jpabook.jpashop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
@SpringBootTest
@Transactional
public class OrderServiceTest {

  @Autowired EntityManager em;
  @Autowired OrderService orderService;
  @Autowired OrderRepository orderRepository;

  @Test
  public void 상품주문() throws Exception {
    // given
    Member member = createMember();
    Book book = createBook("시골 JPA", 10000, 10);

    int orderCount = 2;

    // when
    Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

    // then
    Order getOrder = orderRepository.findOne(orderId);

    assertThat(OrderStatus.ORDER).isEqualTo(getOrder.getOrderStatus());  // 상품 주문시 상태는 ORDER
    assertThat(1).isEqualTo(getOrder.getOrderItems().size());  // 주문한 상품 종류 수가 정확해야한다.
    assertThat(10000 * orderCount).isEqualTo(getOrder.getTotalPrice()); // 주문한 가격은 가격 * 수량이다.
    assertThat(8).isEqualTo(book.getStockQuantity()); // 주문 수량만큼 재고가 줄어야 한다.

  }

  @Test
  public void 상품주문_재고수량초과() throws Exception {
    // given
    Member member = createMember();
    Item item = createBook("시골 JPA", 10000, 10);

    int orderCount = 11;  // 주문 수량

    // when

    // then
    /*
     * 재고 < 주문수량 테스트 성공
     * 재고 > 주문수량 테스트 실패 '재고 수량 예외가 발생해야 한다.' 메세지 발생해야됨
     */
    assertThrows(NotEnoughStockException.class, () -> {
      orderService.order(member.getId(), item.getId(), orderCount);
    }, "재고 수량 예외가 발생해야 한다.");
  }

  @Test
  public void 주문취소() throws Exception {
    // given
    Member member = createMember();
    Book item = createBook("시골 JPA", 10000, 10);

    int orderCount = 2; // 주문 수량
    Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

    // when
    orderService.cancelOrder(orderId);

    // then
    Order getOrder = orderRepository.findOne(orderId);

    assertThat(OrderStatus.CANCEL).isEqualTo(getOrder.getOrderStatus());  // 주문 취소시 주문상태는 CANCEL이 됨.
    assertThat(10).isEqualTo(item.getStockQuantity());  // 주문이 취소된 상품은 취소된 주문만큼 재고 증가
  }

  public Member createMember() {
    Member member = new Member();
    member.setName("회원1");
    member.setAddress(new Address("서울", "강가", "123-123"));
    em.persist(member);

    return member;
  }

  public Book createBook(String name, int price, int stockQuantity) {
    Book book = new Book();
    book.setName(name);
    book.setPrice(price);
    book.setStockQuantity(stockQuantity);
    em.persist(book);

    return book;
  }

}
