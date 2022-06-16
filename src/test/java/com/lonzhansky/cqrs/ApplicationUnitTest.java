package com.lonzhansky.cqrs;

import static org.junit.Assert.assertEquals;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.lonzhansky.patterns.cqrs.agregates.UserAggregate;
import com.lonzhansky.patterns.cqrs.comands.CreateUserCommand;
import com.lonzhansky.patterns.cqrs.comands.UpdateUserCommand;
import org.junit.Before;
import org.junit.Test;

import com.lonzhansky.patterns.cqrs.projections.UserProjection;
import com.lonzhansky.patterns.cqrs.projectors.UserProjector;
import com.lonzhansky.patterns.cqrs.queries.AddressByRegionQuery;
import com.lonzhansky.patterns.cqrs.queries.ContactByTypeQuery;
import com.lonzhansky.patterns.cqrs.repository.UserReadRepository;
import com.lonzhansky.patterns.cqrs.repository.UserWriteRepository;
import com.lonzhansky.patterns.domain.Address;
import com.lonzhansky.patterns.domain.Contact;
import com.lonzhansky.patterns.domain.User;

public class ApplicationUnitTest {

    private UserWriteRepository writeRepository;
    private UserReadRepository readRepository;
    private UserProjector projector;
    private UserAggregate userAggregate;
    private UserProjection userProjection;

    @Before
    public void setUp() {
        writeRepository = new UserWriteRepository();
        readRepository = new UserReadRepository();
        projector = new UserProjector(readRepository);
        userAggregate = new UserAggregate(writeRepository);
        userProjection = new UserProjection(readRepository);
    }

    @Test
    public void givenCQRSApplication_whenCommandRun_thenQueryShouldReturnResult() throws Exception {
        String userId = UUID.randomUUID().toString();
        User user;
        CreateUserCommand createUserCommand = new CreateUserCommand(userId, "Den", "Lonzhansky");
        user = userAggregate.handleCreateUserCommand(createUserCommand);
        projector.project(user);

        UpdateUserCommand updateUserCommand = new UpdateUserCommand(user.getUserid(), Stream.of(new Address("New York", "NY", "10001"), new Address("Los Angeles", "CA", "90001"))
                .collect(Collectors.toSet()),
                Stream.of(new Contact("EMAIL", "lonzhansky@gmail.com"), new Contact("EMAIL", "lonzhansky@ukr.net"))
                        .collect(Collectors.toSet()));
        user = userAggregate.handleUpdateUserCommand(updateUserCommand);
        projector.project(user);

        updateUserCommand = new UpdateUserCommand(userId, Stream.of(new Address("Kyiv", "Podilskyi", "04001"), new Address("Odessa", "Primorskyi", "10101"))
                .collect(Collectors.toSet()),
                Stream.of(new Contact("EMAIL", "lds@gmail.com"))
                        .collect(Collectors.toSet()));
        user = userAggregate.handleUpdateUserCommand(updateUserCommand);
        projector.project(user);

        ContactByTypeQuery contactByTypeQuery = new ContactByTypeQuery(userId, "EMAIL");
        assertEquals(Stream.of(new Contact("EMAIL", "lds@gmail.com"))
                .collect(Collectors.toSet()), userProjection.handle(contactByTypeQuery));

        AddressByRegionQuery addressByRegionQuery = new AddressByRegionQuery(userId, "Podilskyi");
        assertEquals(Stream.of(new Address("Kyiv", "Podilskyi", "04001"))
                .collect(Collectors.toSet()), userProjection.handle(addressByRegionQuery));

    }

}