package com.java.everis.mscreditcard.service;

import com.java.everis.mscreditcard.entity.CreditCard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditCardService {

    Mono<CreditCard> create(CreditCard t);

    Flux<CreditCard> findAll();

    Mono<CreditCard> findById(String id);

    Mono<CreditCard> update(CreditCard t);

    Mono<Boolean> delete(String t);
}
