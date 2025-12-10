package com.example.myfullproject.Controllers;

import com.example.myfullproject.Entities.Book;
import com.example.myfullproject.Entities.People;
import com.example.myfullproject.Services.BookService;
import com.example.myfullproject.Services.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String allBooks(Model model,
                           @RequestParam(value = "page", required = false) Integer page,
                           @RequestParam(value = "books_per_page", required = false) Integer books_per_page,
                           @RequestParam(value = "sort_by_year", required = false, defaultValue = "false")
                           boolean sortByYear) {

        if (page == null || books_per_page == null) {
            model.addAttribute("books", bookService.findAllBooks(sortByYear));
        } else {
            model.addAttribute("books",
                    bookService.findAllWithPagination(page, books_per_page, sortByYear));
        }
        return "allBooks";
    }

    @GetMapping("/available")
    public String availableBooks(Model model) {
        model.addAttribute("books", bookService.getAvailableBooks());
        return "availableBooks";
    }

    @GetMapping("/{id}")
    public String bookById(@PathVariable("id") long id,
                           Model model,
                           @ModelAttribute("person") People person) {

        Book book = bookService.findOne(id);
        model.addAttribute("book", book);

        People owner = bookService.getBookOwner(id);

        if (owner != null) {
            model.addAttribute("owner", owner);
        } else {
            // список людей для выпадающего списка при выдаче
            model.addAttribute("people", peopleService.getAllPeople());
        }

        return "bookById";
    }

    @GetMapping("/bookPage")
    public String page(@ModelAttribute("book") Book book) {
        return "pageForBook";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "pageForBook";

        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/redact")
    public String redact(@PathVariable("id") long id, Model model) {
        model.addAttribute("book", bookService.findOne(id));
        return "redactBook";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") long id,
                         @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "redactBook";

        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/borrow")
    public String borrow(@PathVariable("id") long id,
                         @ModelAttribute("person") People person) {

        bookService.borrow(id, person);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/return")
    public String returnBook(@PathVariable("id") long id) {
        bookService.returnBook(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/addCopies")
    public String addCopies(@PathVariable("id") long id,
                            @RequestParam("additional") int additional) {

        bookService.addCopies(id, additional);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchingPage() {
        return "search";
    }

    @PostMapping("/search")
    public String search(Model model,
                         @RequestParam("query") String query) {

        model.addAttribute("books", bookService.findByTitleStart(query));
        return "search";
    }
}
