package com.ivk23.helloworld.aspects;

import java.util.Optional;

public interface PersonRepository {

    Optional<Person> findById(Long id);

}
