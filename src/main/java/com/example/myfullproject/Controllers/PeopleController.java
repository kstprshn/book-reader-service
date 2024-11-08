package com.example.myfullproject.Controllers;


import com.example.myfullproject.Entities.People;
import com.example.myfullproject.Services.PeopleService;
import com.example.myfullproject.Util.PeopleValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    private final PeopleValidator peopleValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, PeopleValidator peopleValidator) {
        this.peopleService = peopleService;
        this.peopleValidator = peopleValidator;
    }

    @GetMapping()
    public String allPeople(Model model){
        model.addAttribute("all",peopleService.getAllPeople());
        return "allPeople";
    }

    @GetMapping("/{id}")
    public String personById(@PathVariable("id") long id, Model model){
        model.addAttribute("person",peopleService.findOne(id));
        model.addAttribute("personBooks", peopleService.getBookByPersonId(id));
        return "personById";
    }

    @GetMapping("/peoplePage")
    public String page(@ModelAttribute("person") People person){
        return "pageForPeople";
    }

    @PostMapping()
    public String addPerson(@ModelAttribute("person") @Valid People person,
                            BindingResult bindingResult){
        peopleValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "pageForPeople";
        peopleService.save(person);
        return "redirect:/people";
    }
    @GetMapping("/{id}/redact")
    public String redact(@PathVariable("id") long id, Model model){
        model.addAttribute("person", peopleService.findOne(id));
        return "redactPerson";

    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") long id,
                         @ModelAttribute("person") @Valid People person,
                         BindingResult bindingResult){
        peopleValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "redactPerson";
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id){
        peopleService.delete(id);
        return "redirect:/people";
    }
}
