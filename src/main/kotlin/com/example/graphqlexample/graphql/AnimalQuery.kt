package com.example.graphqlexample.graphql

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component

@Component
class AnimalQuery: Query {
    fun animal(type: AnimalType): Animal? = when (type) {
        AnimalType.CAT -> Cat()
        AnimalType.DOG -> Dog()
    }

    fun animals() : List<Animal> {
        val cat = Cat()
        val dog = Dog()

        return listOf(cat, dog)
    }
}

interface Animal {
    val type: AnimalType
    fun sound(): String
}

enum class AnimalType {
    CAT,
    DOG
}

class Dog : Animal {
    override val type = AnimalType.DOG

    override fun sound() = "bark"

    fun barkAtEveryone(): String = "bark at everyone"
}

class Cat : Animal {
    override val type = AnimalType.CAT

    override fun sound() = "meow"

    fun ignoreEveryone(): String = "ignore everyone"
}