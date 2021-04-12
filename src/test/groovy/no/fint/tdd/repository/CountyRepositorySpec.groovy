package no.fint.tdd.repository

import no.fint.tdd.model.County
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import spock.lang.Specification

@DataMongoTest
class CountyRepositorySpec extends Specification {

    @Autowired
    CountyRepository repository

    void cleanup() {
        repository.deleteAll()
    }

    def "Find counties with name starting with"() {
        given:
        def counties = repository.saveAll(Flux.just(
                new County('821311632', 'Vestland fylkeskommune'),
                new County('821227062', 'Vestfold og Telemark fylkeskommune'),
                new County('971045698', 'Rogaland fylkeskommune')))
                .thenMany(repository.findAllByNameStartingWith('Vest'))

        expect:
        StepVerifier.create(counties)
                .expectNextCount(2)
                .verifyComplete()
    }
}