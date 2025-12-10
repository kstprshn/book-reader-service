package com.example.myfullproject.Services;

import com.example.myfullproject.Entities.LogRecord;
import com.example.myfullproject.Repositories.LogRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    private final LogRecordRepository logRecordRepository;

    @Autowired
    public LogService(LogRecordRepository logRecordRepository) {
        this.logRecordRepository = logRecordRepository;
    }

    public void log(String action){
        log(action, null);
    }

    public void log(String action, String details){
        LogRecord record = new LogRecord();
        record.setAction(action);
        record.setDetails(details);
        logRecordRepository.save(record);
    }

}
