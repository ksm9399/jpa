package jpabook.jpashop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.web.BookForm;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

  @GetMapping("/items/new")
  public String createForm(
    Model model
  ) {
    model.addAttribute("form", new BookForm());
    return "items/createItemForm";
  }

  @PostMapping("/items/new")
  public String create(
    BookForm bookForm
  ) {
    Book book = new Book();
    book.setName(bookForm.getName());
    book.setPrice(bookForm.getPrice());
    book.setStockQuantity(bookForm.getStockQuantity());
    book.setAuthor(bookForm.getAuthor());
    book.setIsbn(bookForm.getIsbn());

    itemService.saveItem(book);

    return "redirect:/items";
  }

  @GetMapping("/items")
  public String list(
    Model model
  ) {
    List<Item> items = itemService.findItems();
    model.addAttribute("items", items);
    return "items/itemsList";
  }

  @GetMapping("/items/{itemId}/edit")
  public String updateItemForm(
    @PathVariable("itemId") Long itemId,
    Model model
  ) {
    Book book = (Book) itemService.findOne(itemId);

    BookForm bookForm = new BookForm();
    bookForm.setId(book.getId());
    bookForm.setName(book.getName());
    bookForm.setPrice(book.getPrice());
    bookForm.setStockQuantity(book.getStockQuantity());
    bookForm.setAuthor(book.getAuthor());
    bookForm.setIsbn(book.getIsbn());

    model.addAttribute("form", bookForm);

    return "items/updateItemForm";
  }

  @PostMapping("/items/{itemId}/edit")
  public String updateItem(
    @ModelAttribute("form") BookForm bookForm,
    Model model
  ) {
    Book book = new Book();
    book.setId(bookForm.getId());
    book.setName(bookForm.getName());
    book.setPrice(bookForm.getPrice());
    book.setStockQuantity(bookForm.getStockQuantity());
    book.setAuthor(bookForm.getAuthor());
    book.setIsbn(bookForm.getIsbn());

    itemService.saveItem(book);

    return "redirect:/items";
  }
}
