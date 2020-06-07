/*
 * Copyright 2020 Expedia, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.graphqlexample.config

import com.expediagroup.graphql.execution.FunctionDataFetcher
import com.expediagroup.graphql.execution.SimpleKotlinDataFetcherFactoryProvider
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.schema.DataFetcherFactory
import graphql.schema.DataFetchingEnvironment
import reactor.core.publisher.Mono
import kotlin.reflect.KFunction

/**
 * Custom DataFetcherFactory provider that returns custom Spring based DataFetcherFactory for resolving lateinit properties.
 */
class CustomDataFetcherFactoryProvider(
    private val objectMapper: ObjectMapper
) : SimpleKotlinDataFetcherFactoryProvider(objectMapper) {

    override fun functionDataFetcherFactory(target: Any?, kFunction: KFunction<*>) = DataFetcherFactory {
        CustomFunctionDataFetcher(
                target = target,
                fn = kFunction,
                objectMapper = objectMapper)
    }
}

class CustomFunctionDataFetcher(target: Any?, fn: KFunction<*>, objectMapper: ObjectMapper) : FunctionDataFetcher(target, fn, objectMapper) {

    override fun get(environment: DataFetchingEnvironment): Any? = when (val result = super.get(environment)) {
        is Mono<*> -> result.toFuture()
        else -> result
    }
}

