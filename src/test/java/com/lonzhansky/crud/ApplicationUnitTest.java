package com.lonzhansky.crud;

import com.lonzhansky.patterns.crud.repository.UserRepository;
import com.lonzhansky.patterns.crud.services.UserService;
import com.lonzhansky.patterns.domain.Address;
import com.lonzhansky.patterns.domain.Contact;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ApplicationUnitTest {

    private UserRepository repository;

    @Before
    public void setUp() {
        repository = new UserRepository();
    }

    @Test
    public void givenCRUDApplication_whenDataCreated_thenDataCanBeFetched() throws Exception {
        UserService service = new UserService(repository);
        String userId = UUID.randomUUID()
                .toString();

        service.createUser(userId, "Den", "Lonzhansky");
        service.updateUser(userId, Stream.of(new Contact("EMAIL", "lonzhansky@gmail.com"), new Contact("EMAIL", "lonzhansky@ukr.net"), new Contact("PHONE", "093-403-52-20"))
                        .collect(Collectors.toSet()),
                Stream.of(new Address("Kyiv", "Podilskyi", "04001"), new Address("Odessa", "Primorskyi", "10101"))
                        .collect(Collectors.toSet()));
        service.updateUser(userId, Stream.of(new Contact("EMAIL", "lds@gmail.com"))
                        .collect(Collectors.toSet()),
                Stream.of(new Address("Kyiv", "Shevchenkivskyi", "04001"))
                        .collect(Collectors.toSet()));

        assertEquals(Stream.of(new Contact("EMAIL", "lds@gmail.com"))
                .collect(Collectors.toSet()), service.getContactByType(userId, "EMAIL"));
        assertEquals(Stream.of(new Address("Kyiv", "Shevchenkivskyi", "04001"))
                .collect(Collectors.toSet()), service.getAddressByRegion(userId, "Shevchenkivskyi"));
    }

}

