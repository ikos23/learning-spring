package com.ivk23.helloworld.aspects.sample1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private static final Logger LOG = LoggerFactory.getLogger(PersonRepositoryImpl.class);

    @Override
    public Optional<Person> findById(Long id) {
        final Optional<Person> person = id == 1
                ? of(new Person(1L, "Donald Duck"))
                : empty();
        LOG.info("PersonRepository.findById({}) found {}.", id, person);
        return person;
    }

}
