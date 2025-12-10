package com.example.myfullproject.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "person")
public class People {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

  //  @Pattern(regexp = "[a-zA-Z]+", message = "Incorrect name. The name can only have words...")
    @Size(min = 3, message = "Minimum 3 character must be...")
    @NotEmpty(message = "Name mustn't be empty")
    @Column(name = "name")
    private String name;


    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_birth")
    private Date date_of_birth;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "registered_on")
    private Date registered_on;

    @PrePersist
    public void init(){
        registered_on = new Date();
    }


    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Book> books;

    public People(String name, Date date_of_birth) {
        this.name = name;
        this.date_of_birth = date_of_birth;
    }

    @Override
    public String toString() {
        return "People{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date_of_birth=" + date_of_birth +
                ", email='" + email + '\'' +
                '}';
    }
}