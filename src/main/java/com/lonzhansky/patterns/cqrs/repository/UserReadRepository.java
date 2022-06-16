package com.lonzhansky.patterns.cqrs.repository;

import com.lonzhansky.patterns.domain.User;
import com.lonzhansky.patterns.domain.UserAddress;
import com.lonzhansky.patterns.domain.UserContact;

import java.util.HashMap;
import java.util.Map;

public class UserReadRepository {

    private Map<String, UserAddress> userAddress = new HashMap<>();
    private Map<String, UserContact> userContacts = new HashMap<>();

    public void addUserAddress(String id, UserAddress user) {
        userAddress.put(id, user);
    }

    public UserAddress getUserAddress(String id) {
        return userAddress.get(id);
    }

    public void addUserContacts(String id, UserContact user) {
        userContacts.put(id, user);
    }

    public UserContact getUserContacts(String id) {
        return userContacts.get(id);
    }

}
