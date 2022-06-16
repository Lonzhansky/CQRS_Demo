package com.lonzhansky.patterns.cqrs.comands;

import com.lonzhansky.patterns.domain.Address;
import com.lonzhansky.patterns.domain.Contact;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class UpdateUserCommand {

    private String userId;
    Set<Address> addresses = new HashSet<>();
    Set<Contact> contacts = new HashSet<>();
}
