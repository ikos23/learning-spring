package com.ivk23.helloworld.aspects.sample1;

import java.util.Optional;

public interface PersonRepository {

    Optional<Person> findById(Long id);

}
