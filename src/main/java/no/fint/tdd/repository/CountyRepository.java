package no.fint.tdd.repository;

import no.fint.tdd.model.County;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CountyRepository extends ReactiveMongoRepository<County, String> {

    Flux<County> findAllByNameStartingWith(String name);
}