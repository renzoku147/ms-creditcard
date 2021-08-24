package com.java.everis.mscreditcard.controller;

import com.java.everis.mscreditcard.entity.CreditCard;
import com.java.everis.mscreditcard.service.CreditCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import javax.validation.Valid;

@RestController
@RequestMapping("/creditCard")
@Slf4j
public class CreditCardController {

    @Autowired
    CreditCardService creditCardService;

    @GetMapping("list")
    public Flux<CreditCard> findAll(){
        return creditCardService.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<CreditCard> findById(@PathVariable String id){
        return creditCardService.findById(id);
    }

    @GetMapping("/findCreditCards/{id}")
    public Flux<CreditCard> findCreditCardByCustomer(@PathVariable String id){
        return creditCardService.findCreditCardByCustomer(id);
    }
    
    @GetMapping("/findCreditCardByCardNumber/{numberAccount}")
    public Mono<CreditCard> findCreditCardByCardNumber(@PathVariable String numberAccount){
        return creditCardService.findCreditCardByCardNumber(numberAccount);
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<CreditCard>> create(@Valid @RequestBody CreditCard creditCard){
    	log.info("Esta entrando al metodo");
    	creditCard.setCardNumber(creditCard.getCardNumber().trim());
    	
    	return creditCardService.findCustomerById(creditCard.getCustomer().getId())
    			.flatMap(c -> {
    						log.info("Encontro al cliente " + c.getName());
    						return creditCardService.verifyCardNumber(creditCard.getCardNumber())
									.filter(opt -> {
										log.info("Optional empty > " + opt.isEmpty());
										return opt.isEmpty();
									})
									.flatMap(opt -> {
				                        creditCard.setCustomer(c);
				                        creditCard.setDate(LocalDateTime.now());
				                        return creditCardService.create(creditCard)
			                        		.map(savedCustomer -> new ResponseEntity<>(savedCustomer , HttpStatus.CREATED));
				                    });
    						}
    					).defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PutMapping("/update")
    public Mono<ResponseEntity<CreditCard>> update(@RequestBody CreditCard creditCard) {
        return creditCardService.findById(creditCard.getId()) // REVISO SI EXISTE LA TARJETA DE CREDITO
                .flatMap(cc -> creditCardService.findCustomerById(creditCard.getCustomer().getId())
                                .flatMap(customer -> {
                                                        creditCard.setCustomer(customer);
                                                        return creditCardService.update(creditCard);
                                        }))
                                        .map(cc->new ResponseEntity<>(cc , HttpStatus.CREATED))
                                        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<String>> delete(@PathVariable String id) {
        return creditCardService.delete(id)
                .filter(deleteCustomer -> deleteCustomer)
                .map(deleteCustomer -> new ResponseEntity<>("CreditCard Deleted", HttpStatus.ACCEPTED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
