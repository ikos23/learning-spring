package com.ivk23.helloworld.aspects;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    @Override
    public Optional<Person> findById(Long id) {
        return id == 1
                ? of(new Person(1L, "Donald Duck"))
                : empty();
    }

}
