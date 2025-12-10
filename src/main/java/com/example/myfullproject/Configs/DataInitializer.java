//package com.example.myfullproject.Configs;
//
//import com.example.myfullproject.Entities.Book;
//import com.example.myfullproject.Entities.People;
//import com.example.myfullproject.Repositories.BookRepository;
//import com.example.myfullproject.Repositories.PeopleRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.filter.HiddenHttpMethodFilter;
//
//@Configuration
//public class DataInitializer {
//
//    @Bean
//    CommandLineRunner initDatabase(BookRepository bookRepository, PeopleRepository peopleRepository) {
//        return args -> {
//            if (peopleRepository.count() == 0) {
//                People p1 = new People("Ivanov", null);
//                People p2 = new People("Petrov", null);
//                People p3 = new People("Sidorov", null);
//                peopleRepository.save(p1);
//                peopleRepository.save(p2);
//                peopleRepository.save(p3);
//            }
//
//            if (bookRepository.count() == 0) {
//                Book b1 = new Book("Clean Code", "Robert C. Martin", "2008", "UDC001", 3);
//                Book b2 = new Book("Effective Java", "Joshua Bloch", "2018", "UDC002", 2);
//                Book b3 = new Book("Head First Java", "Kathy Sierra", "2005", "UDC003", 1);
//                bookRepository.save(b1);
//                bookRepository.save(b2);
//                bookRepository.save(b3);
//            }
//        };
//    }
//
//}
