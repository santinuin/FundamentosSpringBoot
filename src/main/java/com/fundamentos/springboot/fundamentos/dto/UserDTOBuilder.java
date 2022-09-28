package com.fundamentos.springboot.fundamentos.dto;

import java.time.LocalDate;

public class UserDTOBuilder {
    private Long id;
    private String name;
    private LocalDate birthDate;

    public UserDTOBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public UserDTOBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserDTOBuilder setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public UserDTO createUserDTO() {
        return new UserDTO(id, name, birthDate);
    }
}