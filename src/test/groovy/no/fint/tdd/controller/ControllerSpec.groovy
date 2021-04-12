package no.fint.tdd.controller

import no.fint.tdd.client.FintClient
import no.fint.tdd.model.County
import no.fint.tdd.model.School
import no.fint.tdd.repository.CountyRepository
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import spock.lang.Specification

@WebFluxTest
class ControllerSpec extends Specification {

    @Autowired
    WebTestClient testClient

    @SpringBean
    CountyRepository repository = Mock()

    @SpringBean
    FintClient client = Stub() {
        getSchools() >> Flux.just(new School())
        getSchool(_ as String) >> Mono.just(new School())
    }

    def "County endpoint returns success and list of all counties"() {
        given:
        repository.findAll() >> Flux.just(new County())

        expect:
        testClient.get()
                .uri('/counties')
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath('$').isArray()
                .jsonPath('$.length()').isEqualTo(1)
    }

    def "County endpoint returns success and one county given valid identifier"() {
        given:
        repository.findById(_ as String) >> Mono.just(new County())

        expect:
        testClient.get()
                .uri('/counties/123')
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath('$.name').isEqualTo(null)
    }

    def "County endpoint returns not found given invalid identifier"() {
        given:
        repository.findById(_ as String) >> Mono.empty()

        expect:
        testClient.get()
                .uri('/counties/456')
                .exchange()
                .expectStatus().isNotFound()
    }

    def "School endpoint returns success and list of all schools"() {
        expect:
        testClient.get()
                .uri('/schools')
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath('$').isArray()
                .jsonPath('$.length()').isEqualTo(1)
    }

    def "School endpoint returns success and one school given valid identifier"() {
        expect:
        testClient.get()
                .uri('/schools/123')
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath('$.name').isEqualTo(null)
    }
}