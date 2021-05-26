package com.project.acmetest.di

import com.project.acmetest.data.auth.*
import com.project.acmetest.data.ticket.ITicketDataSource
import com.project.acmetest.data.ticket.ITicketRepository
import com.project.acmetest.data.ticket.TicketDataSource
import com.project.acmetest.data.ticket.TicketRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideITicketRepository(
        repository: TicketRepository
    ): ITicketRepository

    @Singleton
    @Binds
    abstract fun provideITicketDataSource(
        repository: TicketDataSource
    ): ITicketDataSource

    @Singleton
    @Binds
    abstract fun provideIAuthRepository(
        repository: AuthRepository
    ): IAuthRepository

    @Singleton
    @Binds
    abstract fun provideIAuthDataSource(
        dataSource: AuthDataSource
    ) : IAuthDataSource

    @Singleton
    @Binds
    abstract fun provideIAuthDataSourceProvider(
        source: AuthFakeDataSourceProvider
    ): IAuthDataSourceProvider

}