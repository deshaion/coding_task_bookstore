package test.books.demo.dao;

import test.books.demo.entity.Book;

import java.util.List;

public interface BookRepository {

    List<Book> getAll();

    Book get(String id);
}
