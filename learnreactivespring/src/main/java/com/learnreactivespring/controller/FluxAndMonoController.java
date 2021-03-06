package com.learnreactivespring.controller;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class FluxAndMonoController {

    @GetMapping(value = "/flux", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Integer> returnFlux() {
        return Flux.just(1, 2, 3, 4)
//                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    @GetMapping(value = "/fluxstream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> returnFluxstream() {
        return Flux.just(1, 2, 3, 4, 5).delayElements(Duration.ofSeconds(1)).log();
    }

    @GetMapping(value = "/fluxstream_infinite", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> returnFluxstreamInfinite() {
        return Flux.interval(Duration.ofSeconds(1)).log();
    }

    @GetMapping(value = "/mono", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Integer> returnMono() {
        return Mono.just(1).log();
    }
}
