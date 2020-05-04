package test.books.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.books.demo.dao.BookRepository;
import test.books.demo.entity.Book;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class BookController {

    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping(path = "/books", produces = APPLICATION_JSON_VALUE)
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @GetMapping(path = "/books/{id}", produces = APPLICATION_JSON_VALUE)
    public Book get(@PathVariable String id) {
        return bookRepository.get(id);
    }
}
