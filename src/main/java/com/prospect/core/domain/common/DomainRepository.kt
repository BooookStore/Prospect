package com.prospect.core.domain.common

interface DomainRepository<ENTITY> {

    fun findById(id: String)

    fun save(entity: ENTITY)

    fun remove(entity: ENTITY)

    fun saveAll(entities: Collection<ENTITY>) = entities.forEach { save(it) }

    fun removeAll(entities: Collection<ENTITY>) = entities.forEach { remove(it) }

}