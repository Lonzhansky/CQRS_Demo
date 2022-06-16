package com.lonzhansky.patterns.cqrs.agregates;

import com.lonzhansky.patterns.cqrs.comands.CreateUserCommand;
import com.lonzhansky.patterns.cqrs.comands.UpdateUserCommand;
import com.lonzhansky.patterns.cqrs.repository.UserWriteRepository;
import com.lonzhansky.patterns.domain.Address;
import com.lonzhansky.patterns.domain.Contact;
import com.lonzhansky.patterns.domain.User;

import java.util.Set;

public class UserAggregate {

    private UserWriteRepository writeRepository;

    public UserAggregate(UserWriteRepository userWriteRepository) {
        this.writeRepository = userWriteRepository;
    }

    public User handleCreateUserCommand(CreateUserCommand command) {
        User user = new User(command.getUserId(), command.getFirstName(), command.getLastName());
        writeRepository.addUser(user.getUserid(), user);
        return user;
    }

    public User handleUpdateUserCommand(UpdateUserCommand command) throws Exception {

        User user = writeRepository.getUser(command.getUserId());
        if (user == null) {
            throw new Exception("User does not exist.");
        }
        user.setContacts(command.getContacts());
        user.setAddresses(command.getAddresses());
        writeRepository.addUser(user.getUserid(), user);
        return user;
    }
}
