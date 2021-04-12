package no.fint.tdd.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    @Bean
    public WebClient webClient(final WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl("https://play-with-fint.felleskomponent.no/utdanning/utdanningsprogram")
                .build();
    }
}