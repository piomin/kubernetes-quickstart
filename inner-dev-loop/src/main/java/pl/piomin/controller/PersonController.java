package pl.piomin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.piomin.domain.Person;
import pl.piomin.repository.PersonRepository;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);
    private final PersonRepository repository;

    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Person> getAll() {
        LOG.info("Get all persons");
        return (List<Person>) repository.findAll();
    }

    @GetMapping("/{id}")
    public Person getById(@PathVariable("id") Long id) {
        LOG.info("Get person by id={}", id);
        return repository.findById(id).orElseThrow();
    }

    @GetMapping("/age/{age}")
    public List<Person> getByAgeGreaterThan(@PathVariable("age") int age) {
        LOG.info("Get person by age={}", age);
        return repository.findByAgeGreaterThan(age);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        LOG.info("Delete person by id={}", id);
        repository.deleteById(id);
    }

    @PostMapping
    public Person addNew(@RequestBody Person person) {
        LOG.info("Add new person: {}", person);
        return repository.save(person);
    }

}
