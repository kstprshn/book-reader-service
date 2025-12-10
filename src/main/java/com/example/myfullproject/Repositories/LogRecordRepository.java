package com.example.myfullproject.Repositories;

import com.example.myfullproject.Entities.LogRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;;

@Repository
public interface LogRecordRepository extends JpaRepository<LogRecord, Long> {
}