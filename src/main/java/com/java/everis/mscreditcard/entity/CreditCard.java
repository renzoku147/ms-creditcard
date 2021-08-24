package com.java.everis.mscreditcard.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Document("CreditCard")
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard implements Card{
    private String id;

    @NotNull
    private String cardNumber;

    @NotNull
    private Customer customer;

    @NotNull
    private Double limitCredit;

    @NotNull
    private LocalDate expirationDate;

    private LocalDateTime date;
}
