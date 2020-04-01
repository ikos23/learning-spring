package com.ivk23.helloworld.aspects.sample1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringConfiguration.class })
class PersonRepositoryAspectTest {

    @Autowired
    private PersonRepository classUnderTest;

    @Test
    public void findById() {
        final var person = classUnderTest.findById(1L);
        assertTrue(person.isPresent());

        // The output (demos how aspect is applied to a target object :)
        // PersonRepositoryAspect.beforeFindBy - >>>> Method [PersonRepository.findById] is about to be called.
        // PersonRepositoryImpl.findById - PersonRepository.findById(1) found Optional[Person {id=1, fullName='Donald Duck'}].
        // PersonRepositoryAspect.afterFindBy - >>>> Method [PersonRepository.findById] has just been called.
    }

}