package com.example.graphqlexample.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GraphQLConfig {
    @Bean
    fun hooks() = CustomSchemaGeneratorHooks()

    @Bean
    fun dataFetcherFactoryProvider(objectMapper: ObjectMapper) = CustomDataFetcherFactoryProvider(objectMapper)
}