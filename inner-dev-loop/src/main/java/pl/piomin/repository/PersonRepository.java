package pl.piomin.repository;

import org.springframework.data.repository.CrudRepository;
import pl.piomin.domain.Person;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {
    List<Person> findByAgeGreaterThan(int age);
}
