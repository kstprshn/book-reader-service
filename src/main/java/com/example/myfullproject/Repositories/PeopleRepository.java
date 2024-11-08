package com.example.myfullproject.Repositories;


import com.example.myfullproject.Entities.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<People, Long> {
    Optional<People> findByName(String str);
}
