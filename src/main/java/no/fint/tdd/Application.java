package no.fint.tdd;

import no.fint.tdd.model.County;
import no.fint.tdd.repository.CountyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    CountyRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        repository.saveAll(List.of(
                new County("821311632", "Vestland fylkeskommune"),
                new County("821227062", "Vestfold og Telemark fylkeskommune"),
                new County("971045698", "Rogaland fylkeskommune")
        ));
    }
}