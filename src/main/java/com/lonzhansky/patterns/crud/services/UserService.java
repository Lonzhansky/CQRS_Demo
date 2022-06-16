package com.lonzhansky.patterns.crud.services;

import com.lonzhansky.patterns.crud.repository.UserRepository;
import com.lonzhansky.patterns.domain.Address;
import com.lonzhansky.patterns.domain.Contact;
import com.lonzhansky.patterns.domain.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(String id, String firstname, String lastname) {
        User user = new User(id, firstname, lastname);
        userRepository.addUser(id, user);
    }

    public void updateUser(String id, Set<Contact> contacts, Set<Address> addresses) throws Exception {
        User user = userRepository.getUser(id);
        if (user == null) {
            throw new Exception("User does not exist.");
        }

        user.setContacts(contacts);
        user.setAddresses(addresses);
        userRepository.addUser(id, user);
    }

    public Set<Contact> getContactByType(String userId, String contactType) throws Exception {
        User user = userRepository.getUser(userId);
        if (user == null) {
            throw new Exception("User does not exist.");
        }
        Set<Contact> contacts = user.getContacts();
        return contacts.stream().filter(c -> c.getType().equals(contactType)).collect(Collectors.toSet());
    }

    public Set<Address> getAddressByRegion(String userId, String state) throws Exception {
        User user = userRepository.getUser(userId);
        if (user == null) {
            throw new Exception("User does not exist.");
        }
        Set<Address> addresses = user.getAddresses();
        return addresses.stream().filter(c -> c.getState().equals(state)).collect(Collectors.toSet());
    }
}
