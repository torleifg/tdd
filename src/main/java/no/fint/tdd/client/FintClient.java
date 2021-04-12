package no.fint.tdd.client;

import no.fint.model.resource.utdanning.utdanningsprogram.SkoleResource;
import no.fint.model.resource.utdanning.utdanningsprogram.SkoleResources;
import no.fint.tdd.model.School;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class FintClient {
    private final WebClient webClient;

    public FintClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<School> getSchools() {
        return webClient.get()
                .uri("/skole")
                .retrieve()
                .bodyToMono(SkoleResources.class)
                .flatMapIterable(SkoleResources::getContent)
                .map(School::fromSkoleResource);
    }

    public Mono<School> getSchool(String id) {
        return webClient.get()
                .uri("/skole/systemid/{id}", id)
                .retrieve()
                .bodyToMono(SkoleResource.class)
                .map(School::fromSkoleResource);
    }
}