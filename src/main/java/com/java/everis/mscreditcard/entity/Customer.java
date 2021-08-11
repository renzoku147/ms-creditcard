package com.java.everis.mscreditcard.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class Customer {

    String id;

    String name;

    String lastName;

    TypeCustomer typeCustomer;

    String dni;

    Integer age;

    String gender;
}
