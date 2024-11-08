package com.example.myfullproject.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long id;

    @NotEmpty(message = "Title can not be empty")
    @Size(min = 3, message = "Min title is 3 characters...")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Incorrect title. Title may has only words and numbers")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Author can not be empty")
    @Size(min = 3, message = "Min title is 3 characters...")
    @Column(name = "author")
    private String author;

    @NotEmpty(message = "Year mustn't be empty")
    @Pattern(regexp = "\\d{4}", message = "The date should be in format: YYYY")
    @Column(name = "year_of_publication")
    private String year;

    @Column(name="taken_at")
    private Date taken_at;

    public void init(){
        taken_at = new Date();
    }

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private People person;

    public Book(String title, String author, String year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year_of_publication='" + year + '\'' +
                ", taken_at=" + taken_at +
                '}';
    }
}
