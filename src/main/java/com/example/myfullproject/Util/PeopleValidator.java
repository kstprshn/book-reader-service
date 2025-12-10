package com.example.myfullproject.Util;

import com.example.myfullproject.Entities.People;
import com.example.myfullproject.Services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PeopleValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PeopleValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return People.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        People people = (People)target;
        if(peopleService.findByName(people.getName()).isPresent()){
            errors.rejectValue("name", "", "This name exists");
        }
    }
}