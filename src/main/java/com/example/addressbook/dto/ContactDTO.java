package com.example.addressbook.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ContactDTO {
    private String name;
    private String phone;
    private String email;
    private String address;
}
