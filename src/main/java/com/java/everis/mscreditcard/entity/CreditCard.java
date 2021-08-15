package com.java.everis.mscreditcard.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Document("CreditCard")
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {
    @NotNull
    private String id;

    @NotEmpty
    private String cardNumber;

    private Customer customer;

    @NotNull
    private Double limitCredit;

    @NotNull
    private LocalDate expirationDate;

    @NotNull
    private LocalDateTime date;
}
