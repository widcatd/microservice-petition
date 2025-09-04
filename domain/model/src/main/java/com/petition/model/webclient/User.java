package com.petition.model.webclient;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private Long idUser;
    private String firstName;
    private String lastName;
    private LocalDate dateBirthday;
    private String address;
    private String phone;
    private String email;
    private BigDecimal salaryBase;
    private String identityDocument;
}
