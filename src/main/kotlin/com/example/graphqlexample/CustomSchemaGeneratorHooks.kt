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

package com.example.graphqlexample

import com.expediagroup.graphql.hooks.SchemaGeneratorHooks
import org.springframework.beans.factory.BeanFactoryAware
import reactor.core.publisher.Mono
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.isSubclassOf

/**
 * Schema generator hook that adds additional scalar types.
 */
class CustomSchemaGeneratorHooks() : SchemaGeneratorHooks {

    /**
     * Register Reactor Mono monad type.
     */
    override fun willResolveMonad(type: KType): KType = when (type.classifier) {
        Mono::class -> type.arguments.firstOrNull()?.type
        else -> type
    } ?: type

}
