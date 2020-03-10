package com.learnreactivespring.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest
public class FluxAndMonoControllerTest {

    @Autowired
    private WebTestClient m_webTestClient;

    @Test
    public void flux_approach1() {
        Flux<Integer> integerFlux = m_webTestClient.get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(integerFlux)
                .expectSubscription()
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .verifyComplete();
    }

    @Test
    public void flux_approach2() {
        m_webTestClient.get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBodyList(Integer.class) // converts result to list of integers
                .hasSize(4);
    }

    @Test
    public void flux_approach3() {
        List<Integer> expected = Arrays.asList(1, 2, 3, 4);
        EntityExchangeResult<List<Integer>> entityExchangeResult = m_webTestClient.get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Integer.class) // converts result to list of integers
                .returnResult();

        assertEquals(expected, entityExchangeResult.getResponseBody());
    }

    @Test
    public void flux_approach4() {
        List<Integer> expected = Arrays.asList(1, 2, 3, 4);
        m_webTestClient.get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Integer.class) // converts result to list of integers
                .consumeWith((response) -> {
                    assertEquals(expected, response.getResponseBody());
                });
    }

    @Test
    public void fluxstream() {
        Flux<Long> returnFluxStream = m_webTestClient.get()
                .uri("/fluxstream_infinite")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Long.class) // converts result to list of integers
                .getResponseBody();

        StepVerifier.create(returnFluxStream)
                .expectNext(0l)
                .expectNext(1l)
                .expectNext(2l)
                .expectNext(3l)
                .thenCancel()
                .verify();
    }

    @Test
    public void mono() {
        m_webTestClient.get()
                .uri("/mono")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Integer.class)
                .consumeWith((response) -> {
                    assertEquals(1, response.getResponseBody());
                });
    }

}
