package com.lonzhansky.patterns.cqrs.comands;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserCommand {

    private String userId;
    private String firstName;
    private String lastName;
}
