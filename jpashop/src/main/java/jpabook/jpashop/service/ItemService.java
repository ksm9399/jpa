package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;

  @Transactional
  public void saveItem(Item item) {
    itemRepository.save(item);
  }

  // 변경 감지에 의한 데이터 업데이트
  // merge의 경우 값이 없으면 null로 바뀔 위험이 있음
  // 실무에서 merge사용 X, 변경감지에 의한 업데이트 사용, 변경될 데이터만 한땀한땀 셋해주는게 좋음
  @Transactional
  public void updateItem(Long itemId, int price, String name, int stock) {
    Item findItem = itemRepository.findOne(itemId);
    findItem.setPrice(price);
    findItem.setName(name);
    findItem.setStockQuantity(stock);
  }

  public List<Item> findItems() {
    return itemRepository.findAll();
  }

  public Item findOne(Long itemId) {
    return itemRepository.findOne(itemId);
  }
}
