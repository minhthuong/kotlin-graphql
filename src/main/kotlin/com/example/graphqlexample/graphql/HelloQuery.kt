package com.example.graphqlexample.graphql

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

data class Widget(val id: Int, val value: String?)

@Component
class HelloQuery: Query {
    fun helloWorld() = "Hello World!"
    fun helloWorldMono() =  Mono.just("Hello World, Mono!")
}


@Component
class WidgetQuery: Query {
    fun widgetById(id: Int): Widget? = Widget(id, "some_value")
}
