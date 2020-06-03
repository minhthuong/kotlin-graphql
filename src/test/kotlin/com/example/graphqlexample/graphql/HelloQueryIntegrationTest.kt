package com.example.graphqlexample.graphql

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import org.springframework.web.reactive.function.BodyInserter
import org.springframework.web.reactive.function.BodyInserters

@SpringBootTest
@AutoConfigureWebTestClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HelloQueryIntegrationTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun `should return Hello World`() {
        webTestClient.post()
                .uri("/graphql")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType("application", "graphql"))
                .bodyValue("query { helloWorld }")
                .exchange()
                .expectBody()
                .jsonPath("$.data.helloWorld").isEqualTo("Hello World!")

//        val query = "helloWorld"
//        val expectedData = "Hello World!"
//
//        webTestClient.post()
//                .uri("/graphql")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType("application", "graphql"))
//                .bodyValue("query { helloWorld }")
//                .exchange()
//                .expectBody()
//                .jsonPath("$.data.$query").exists()
//                .jsonPath("$.data.$query").isEqualTo(expectedData)

    }
}
