// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package de.bytefish.multitenancy.web.controllers;

import de.bytefish.multitenancy.model.Customer;
import de.bytefish.multitenancy.repositories.ICustomerRepository;
import de.bytefish.multitenancy.web.converter.Converters;
import de.bytefish.multitenancy.web.model.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CustomerController {

    private final ICustomerRepository customerRepository;

    @Autowired
    public CustomerController(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    public Flux<CustomerDto> getAll() {
        return customerRepository.findAll().map(Converters::convert);
    }

    @GetMapping("/customers/{id}")
    public Mono<CustomerDto> get(@PathVariable("id") long id) {
        return customerRepository.findById(id).map(Converters::convert);
    }

    @PostMapping("/customers")
    public Mono<CustomerDto> post(@RequestBody CustomerDto customer) {
        Customer source = Converters.convert(customer);

        return customerRepository
                .save(source)
                .map(Converters::convert);
    }

    @DeleteMapping("/customers/{id}")
    public Mono<Void> delete(@PathVariable("id") long id) {
        return customerRepository.deleteById(id);
    }
}
