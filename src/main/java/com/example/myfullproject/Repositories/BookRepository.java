package com.example.myfullproject.Repositories;


import com.example.myfullproject.Entities.Book;
import com.example.myfullproject.Entities.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String str);

    List<Book> findByTitleStartingWith(String str);
    List<Book> findByPerson(People person);

}
