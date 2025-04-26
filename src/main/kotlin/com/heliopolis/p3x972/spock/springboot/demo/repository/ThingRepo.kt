package com.heliopolis.p3x972.spock.springboot.demo.repository

import com.heliopolis.p3x972.spock.springboot.demo.entity.Thing
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime.now
import java.util.*


@Repository
class ThingRepo: CrudRepository<Thing, Int> {

    private val storage = emptyList<Thing>().toMutableList()

    init {
        storage.add(Thing(0, "TARDIS", now().minusDays(2)))
        storage.add(Thing(1, "Holocron", now().minusYears(2000)))
        storage.add(Thing(2, "Omega 13", now().minusYears(10)))
    }

    override fun <S : Thing> save(entity: S): S {
        delete(entity)
        entity.updateDate = now()
        storage.add(entity)
        return entity
    }

    override fun <S : Thing> saveAll(entities: Iterable<S>): Iterable<S> {
        entities.forEach { save(it) }
        return entities
    }

    override fun findById(integer: Int): Optional<Thing> {
        return Optional.ofNullable(storage.find { t: Thing -> integer == t.id })
    }

    override fun existsById(integer: Int): Boolean {
        return storage.any { t: Thing -> integer == t.id }
    }

    override fun findAll(): Iterable<Thing> {
        return storage
    }

    override fun findAllById(integers: Iterable<Int>): Iterable<Thing> {
        val ids = integers.toSet()
        return storage.stream().filter { t: Thing -> ids.contains(t.id) }.toList()
    }

    override fun count(): Long {
        return storage.size.toLong()
    }

    override fun deleteById(integer: Int) {
        storage.removeIf { t: Thing -> integer == t.id }
    }

    override fun delete(entity: Thing) {
        deleteById(entity.id)
    }

    override fun deleteAll(entities: Iterable<Thing>) {
        storage.forEach { t: Thing -> delete(t) }
    }

    override fun deleteAll() {
        storage.clear()
    }

    override fun deleteAllById(ids: Iterable<Int>) {
        ids.forEach { deleteById(it) }
    }
}