package com.java.everis.mscreditcard.service.impl;

import com.java.everis.mscreditcard.entity.Card;
import com.java.everis.mscreditcard.entity.CreditCard;
import com.java.everis.mscreditcard.entity.Customer;
import com.java.everis.mscreditcard.entity.DebitCard;
import com.java.everis.mscreditcard.repository.CreditCardRepository;
import com.java.everis.mscreditcard.service.CreditCardService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    WebClient webClient = WebClient.create("http://localhost:8887/ms-customer/customer");
    
    WebClient webClientDebitCard = WebClient.create("http://localhost:8887/ms-debitcard/debitCard");

    @Autowired
    CreditCardRepository creditCardRepository;

    @Override
    public Mono<CreditCard> create(CreditCard t) {
        return creditCardRepository.save(t);
    }

    @Override
    public Flux<CreditCard> findAll() {
        return creditCardRepository.findAll();
    }

    @Override
    public Mono<CreditCard> findById(String id) {
        return creditCardRepository.findById(id);
    }

    @Override
    public Mono<CreditCard> update(CreditCard t) {
        return creditCardRepository.save(t);
    }

    @Override
    public Mono<Boolean> delete(String t) {
        return creditCardRepository.findById(t)
                .flatMap(credit -> creditCardRepository.delete(credit).then(Mono.just(Boolean.TRUE)))
                .defaultIfEmpty(Boolean.FALSE);
    }

    @Override
    public Mono<Customer> findCustomerById(String id) {
        return webClient.get().uri("/find/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Customer.class);
    }

    @Override
    public Flux<CreditCard> findCreditCardByCustomer(String id) {
        return creditCardRepository.findByCustomerId(id);
    }

	@Override
	public Mono<Optional<Card>> verifyCardNumber(String cardNumber) {
		return creditCardRepository.findByCardNumber(cardNumber)
				.map(cc -> Optional.of((Card)cc))
				.switchIfEmpty(webClientDebitCard.get().uri("/findCreditCardByCardNumber/{cardNumber}", cardNumber)
	                    .accept(MediaType.APPLICATION_JSON)
	                    .retrieve()
	                    .bodyToMono(DebitCard.class)
	                    .map(savingAccount -> {
	                        System.out.println("Encontro savingAccount > " + savingAccount.getId());
	                        return Optional.of((Card)savingAccount);
	                    })
						.defaultIfEmpty(Optional.empty()));
	}

	@Override
	public Mono<CreditCard> findCreditCardByCardNumber(String cardNumber) {
		return creditCardRepository.findByCardNumber(cardNumber);
	}
}