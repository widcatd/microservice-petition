package com.petition.usecase.petition.mock;

import com.petition.model.webclient.User;

import java.math.BigDecimal;

public class UserMock {
    public static User validUser(String email) {
        return User.builder()
                .email(email)
                .salaryBase(BigDecimal.valueOf(3500.0))
                .build();
    }
}
