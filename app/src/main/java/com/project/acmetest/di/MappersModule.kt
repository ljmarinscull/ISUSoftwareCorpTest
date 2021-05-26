package com.project.acmetest.di

import com.project.acmetest.data.model.TicketEntity
import com.project.acmetest.data.model.TicketObject
import com.project.acmetest.data.mappers.IMapper
import com.project.acmetest.data.mappers.TicketDataMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MappersModule {

    @Binds
    abstract fun provideIMapper(
        mapper: TicketDataMapper
    ): IMapper<TicketObject, TicketEntity>

}