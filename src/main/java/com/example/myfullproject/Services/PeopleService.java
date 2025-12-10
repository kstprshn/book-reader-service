package com.example.myfullproject.Services;

import com.example.myfullproject.Entities.Book;
import com.example.myfullproject.Entities.People;
import com.example.myfullproject.Repositories.BookRepository;
import com.example.myfullproject.Repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final BookRepository bookRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BookRepository bookRepository) {
        this.peopleRepository = peopleRepository;
        this.bookRepository = bookRepository;
    }

    public List<People> getAllPeople() {
        return peopleRepository.findAll();
    }

    public People findOne(long id) {
        return peopleRepository.findById(id).orElse(null);
    }

    public Optional<People> findByName(String str){
        return peopleRepository.findByName(str);
    }

    @Transactional
    public void save(People person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(long id, People newData) {
        newData.setId(id);
        peopleRepository.save(newData);
    }
    @Transactional
    public void delete(long id) {
        peopleRepository.deleteById(id);
    }

    public List<Book> getBookByPersonId(long id) {
        People person = peopleRepository.findById(id).orElse(null);
//        return person.getBooks();
        return bookRepository.findByPerson(person);
    }
}