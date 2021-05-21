package com.project.acmetest.data.mappers

/**
 * Mapper interface.
 */
interface IMapper<I, O> {
    fun map(input: I): O
    fun mapReverse(input: O): I
}