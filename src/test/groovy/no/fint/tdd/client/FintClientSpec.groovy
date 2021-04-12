package no.fint.tdd.client

import com.fasterxml.jackson.databind.ObjectMapper
import no.fint.model.felles.kompleksedatatyper.Identifikator
import no.fint.model.resource.utdanning.utdanningsprogram.SkoleResource
import no.fint.model.resource.utdanning.utdanningsprogram.SkoleResources
import no.fint.tdd.model.School
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.test.StepVerifier
import spock.lang.Specification

class FintClientSpec extends Specification {
    MockWebServer mockWebServer = new MockWebServer()

    FintClient client

    def setup() {
        mockWebServer.start()

        def baseUrl = mockWebServer.url('/').toString()

        client = new FintClient(WebClient.builder().baseUrl(baseUrl).build())
    }

    def cleanup() {
        mockWebServer.shutdown()
    }

    def "Get skoleresources from FINT is success and translates body to school object"() {
        given:
        SkoleResource resource = new SkoleResource(systemId: new Identifikator(identifikatorverdi: '993527084'),
                navn: 'Nordahl Grieg videregående skole')

        SkoleResources resources = new SkoleResources()
        resources.addResource(resource)

        mockWebServer.enqueue(new MockResponse()
                .setBody(new ObjectMapper().writeValueAsString(resources))
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setResponseCode(HttpStatus.OK.value()))

        when:
        def schools = client.getSchools()

        then:
        StepVerifier.create(schools)
                .expectNext(School.fromSkoleResource(resource))
                .verifyComplete()
    }

    def "Get skoleresources from FINT fails and exception is thrown"() {
        given:
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value()))

        when:
        def schools = client.getSchools()

        then:
        StepVerifier.create(schools)
                .expectError(WebClientResponseException.class)
                .verify()
    }

    def "Get skoleresource from FINT is success and translates body to school object"() {
        given:
        SkoleResource resource = new SkoleResource(systemId: new Identifikator(identifikatorverdi: '993527084'),
                navn: 'Nordahl Grieg videregående skole')

        mockWebServer.enqueue(new MockResponse()
                .setBody(new ObjectMapper().writeValueAsString(resource))
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setResponseCode(HttpStatus.OK.value()))

        when:
        def schools = client.getSchool(_ as String)

        then:
        StepVerifier.create(schools)
                .expectNext(School.fromSkoleResource(resource))
                .verifyComplete()
    }

    def "Get skoleresource from FINT fails and exception is thrown"() {
        given:
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(HttpStatus.NOT_FOUND.value()))

        when:
        def schools = client.getSchool(_ as String)

        then:
        StepVerifier.create(schools)
                .expectError(WebClientResponseException.class)
                .verify()
    }
}