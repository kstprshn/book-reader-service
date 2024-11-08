package com.example.myfullproject.Services;


import com.example.myfullproject.Entities.Book;
import com.example.myfullproject.Entities.People;
import com.example.myfullproject.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;


    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooks(boolean sortByYear) {
        if (sortByYear) {
            return bookRepository.findAll(Sort.by("year"));
        } else
            return bookRepository.findAll();
    }

    public List<Book> findAllWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
        if (sortByYear) {
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        } else
            return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public Book findOne(long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> findByTitleStart(String str){
        return bookRepository.findByTitleStartingWith(str);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(long id, Book newBook) {
        newBook.setId(id);
        bookRepository.save(newBook);
    }

    @Transactional
    public void delete(long id) {
        bookRepository.deleteById(id);
    }

    //книга с указанным ее id
    public People getBookOwner(long id) {

//        Book b = bookRepository.findById(id).orElse(null);
//        return b.getPerson();

        return bookRepository.findById(id).map(Book::getPerson).orElse(null);
    }

    @Transactional
    public void cleanBook(long id) {
        bookRepository.findById(id).ifPresent(b -> b.setPerson(null));
        bookRepository.findById(id).ifPresent(book -> book.setTaken_at(null));
    }

    @Transactional
    public void setBook(long book_id, People chosenPerson) {
        bookRepository.findById(book_id).ifPresent(b -> b.setPerson(chosenPerson));
        Objects.requireNonNull(bookRepository.findById(book_id).orElse(null)).init();
    }

}
