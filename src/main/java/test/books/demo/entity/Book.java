package test.books.demo.entity;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
public class Book {
    String id;
    String name;
    String details;
    BigDecimal price;
    String image;
    List<Book> similarBooks;
}
