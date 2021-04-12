package no.fint.tdd.controller;

import no.fint.tdd.client.FintClient;
import no.fint.tdd.exception.NotFoundException;
import no.fint.tdd.model.County;
import no.fint.tdd.model.School;
import no.fint.tdd.repository.CountyRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class Controller {
    private final CountyRepository repository;
    private final FintClient client;

    public Controller(CountyRepository repository, FintClient client) {
        this.repository = repository;
        this.client = client;
    }

    @GetMapping("counties")
    public Flux<County> getCounties() {
        return repository.findAll();
    }

    @GetMapping("counties/{id}")
    public Mono<County> getCounty(@PathVariable String id) {
        return repository.findById(id).switchIfEmpty(Mono.error(NotFoundException::new));
    }

    @GetMapping("schools")
    public Flux<School> getSchools() {
        return client.getSchools();
    }

    @GetMapping("schools/{id}")
    public Mono<School> getSchool(@PathVariable String id) {
        return client.getSchool(id).switchIfEmpty(Mono.error(NotFoundException::new));
    }
}