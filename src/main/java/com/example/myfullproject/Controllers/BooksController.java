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
                           @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {
        if (page == null || books_per_page == null) {
            model.addAttribute("books", bookService.findAllBooks(sortByYear));
        } else
            model.addAttribute("books", bookService.findAllWithPagination(page, books_per_page, sortByYear));
        return "allBooks";
    }

    @GetMapping("/{id}")
    public String bookById(@PathVariable("id") long id, Model model, @ModelAttribute("person") People person) {
        model.addAttribute("book", bookService.findOne(id));

        People bookOwner = bookService.getBookOwner(id);
        if (bookOwner != null)
            model.addAttribute("owner", bookOwner);
        else
            model.addAttribute("people", peopleService.getAllPeople());

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

    @PatchMapping("/{id}/set")
    public String setBook(@PathVariable("id") long id, @ModelAttribute("person") People selectedPerson) {
        bookService.setBook(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/clean")
    public String cleanBook(@PathVariable("id") long id) {
        bookService.cleanBook(id);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchingPage() {
        return "search";
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam("query") String query){
        model.addAttribute("books", bookService.findByTitleStart(query));
        return "search";
    }
}
