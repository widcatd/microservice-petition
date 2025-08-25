package com.petition.api.webclient.dto;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserDto {
    private String idUser;
    private String firstName;
    private String lastName;
    private String dateBirthday;
    private String direction;
    private String phone;
    private String email;
    private Long salaryBase;
}
