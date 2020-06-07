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

import com.expediagroup.graphql.hooks.SchemaGeneratorHooks
import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.GraphQLScalarType
import graphql.schema.GraphQLType
import reactor.core.publisher.Mono
import java.time.LocalDate
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.typeOf

/**
 * Schema generator hook that adds additional scalar types.
 */
class CustomSchemaGeneratorHooks : SchemaGeneratorHooks {

    /**
     * Register Reactor Mono monad type.
     */
    override fun willResolveMonad(type: KType): KType = when (type.classifier) {
        Mono::class -> type.arguments.firstOrNull()?.type
        else -> type
    } ?: type

    override fun willGenerateGraphQLType(type: KType): GraphQLType? = when (type.classifier as? KClass<*>) {
        LocalDate::class -> graphqlDateType
        else -> null
    }

    val graphqlDateType = GraphQLScalarType.newScalar()
            .name("Date")
            .description("A type representing Date")
            .coercing(DateCoercing)
            .build()

    object DateCoercing : Coercing<LocalDate, String> {
        override fun parseValue(input: Any?): LocalDate {
            return LocalDate.parse(input.toString())
        }

        override fun parseLiteral(input: Any?): LocalDate {
            val dateString = (input as? StringValue)?.value
            return LocalDate.parse(dateString)
        }

        override fun serialize(datafetcherResult: Any): String {
            return datafetcherResult.toString()
        }
    }

}
