package com.example.graphqlexample.graphql

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class HelloQuery: Query {

    fun helloWorld() = "Hello World!"
    fun helloWorldMono() =  Mono.just("Hello World, Mono!")
}
@Component
class WidgetQuery: Query {
    fun widgetById(id: Int, type: String): WidgetI? = if(type == "0") Widget(id, "some_value") else Widget1(id, "value 1")
}

data class Widget(
     override val id: Int,
     val value: String?
): WidgetI

data class Widget1(
        override val id: Int,
        val value1: String?
): WidgetI

interface WidgetI {
    val id: Int
}
