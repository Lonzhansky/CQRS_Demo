package com.lonzhansky.patterns.cqrs.projections;

import com.lonzhansky.patterns.cqrs.queries.AddressByRegionQuery;
import com.lonzhansky.patterns.cqrs.queries.ContactByTypeQuery;
import com.lonzhansky.patterns.cqrs.repository.UserReadRepository;
import com.lonzhansky.patterns.domain.*;

import java.util.Set;
import java.util.stream.Collectors;

public class UserProjection {

    private UserReadRepository repository;

    public UserProjection(UserReadRepository repository) {
        this.repository = repository;
    }

    public Set<Contact> handle(ContactByTypeQuery query) throws Exception {
        UserContact userContact = repository.getUserContacts(query.getUserId());
        if (userContact == null) {
            throw new Exception("User does not exist.");
        }
        return userContact.getContactByType().get(query.getContactType());
    }

    public Set<Address> handle(AddressByRegionQuery query) throws Exception {
        UserAddress  userAddress = repository.getUserAddress(query.getUserId());
        if (userAddress == null) {
            throw new Exception("User does not exist.");
        }
        return userAddress.getAddressByRegion().get(query.getState());
    }


}
