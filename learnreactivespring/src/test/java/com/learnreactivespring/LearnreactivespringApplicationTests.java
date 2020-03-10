package com.learnreactivespring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class LearnreactivespringApplicationTests {

    @Test
    public void fluxTest() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Stpring")
//                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("Ryan"))
                .log();
        stringFlux.subscribe(System.out::println, (e) -> System.err.println(e), () -> System.out.println("Completed"));
    }

    @Test
    public void fluxTest_withoutError() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring").log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .verifyComplete();
    }

    @Test
    public void fluxTest_withError() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void fluxTestElementsCount_withError() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();
        StepVerifier.create(stringFlux).expectNextCount(3).expectError(RuntimeException.class).verify();
    }

    @Test
    public void fluxTestElementsCount_withError1() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring", "Spring Boot", "Reactive Spring")
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void monoTest() {
        Mono<String> stringMono = Mono.just("Spring");
        StepVerifier.create(stringMono).expectNext("Spring").verifyComplete();
    }

    @Test
    public void monoTest_error() {
        Mono<String> stringMono = Mono.error(new RuntimeException("Error here"));
        StepVerifier.create(stringMono).expectError(RuntimeException.class).verify();
    }

}
