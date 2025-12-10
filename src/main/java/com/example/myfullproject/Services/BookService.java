package com.example.myfullproject.Services;


import com.example.myfullproject.Entities.Book;
import com.example.myfullproject.Entities.People;
import com.example.myfullproject.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    private final LogService logService;


    @Autowired
    public BookService(BookRepository bookRepository, LogService logService) {
        this.bookRepository = bookRepository;
        this.logService = logService;
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
        return bookRepository.findById(id).map(Book::getPerson).orElse(null);
    }


    @Transactional
    public void borrow (long book_id, People person){
        Book book = bookRepository.findById(book_id).orElseThrow(IllegalStateException::new);

        if(book.getCopies() < 1){
            throw new IllegalStateException();
        }

        book.setCopies( book.getCopies() - 1);
        book.setPerson(person);
        book.init();
    }

    @Transactional
    public void returnBook(long book_id){
        Book book = bookRepository.findById(book_id).orElseThrow(IllegalArgumentException::new);
        book.setCopies(book.getCopies() + 1);
        book.setPerson(null);
        book.setTaken_at(null);
    }

    @Transactional
    public void addCopies(long bookId, int additional) {
        logService.log("ADD_COPIES", "bookId=" + bookId + ", additional=" + additional);
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalStateException("Book not found with id " + bookId));
        if (additional <= 0) {
            logService.log("ADD_COPIES_INVALID", "bookId=" + bookId + ", additional=" + additional);
            throw new IllegalArgumentException("additional must be > 0");
        }
        book.setCopies(book.getCopies() + additional);
        logService.log("ADD_COPIES_SUCCESS", "bookId=" + bookId + ", newCopies=" + book.getCopies());
    }

    public List<Book> getAvailableBooks(){
        return bookRepository.findAll()
                .stream()
                .filter(el -> el.getCopies() > 0)
                .collect(Collectors.toList());
    }

}