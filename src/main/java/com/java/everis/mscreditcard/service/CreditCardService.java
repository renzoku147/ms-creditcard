package com.java.everis.mscreditcard.service;

import java.util.Optional;

import com.java.everis.mscreditcard.entity.Card;
import com.java.everis.mscreditcard.entity.CreditCard;
import com.java.everis.mscreditcard.entity.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditCardService {

    Mono<CreditCard> create(CreditCard t);

    Flux<CreditCard> findAll();

    Mono<CreditCard> findById(String id);

    Mono<CreditCard> update(CreditCard t);

    Mono<Boolean> delete(String t);

    Mono<Customer> findCustomerById(String id);

    Flux<CreditCard> findCreditCardByCustomer(String id);
    
    Mono<CreditCard> findCreditCardByCardNumber(String cardNumber);
    
    Mono<Optional<Card>> verifyCardNumber(String t);
}
