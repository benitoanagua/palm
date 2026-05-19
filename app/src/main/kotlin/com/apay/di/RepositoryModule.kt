package com.apay.di

import com.apay.data.repository.*
import com.apay.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton
    abstract fun bindTasaRepository(impl: FirestoreTasaRepository): TasaRepository

    @Binds @Singleton
    abstract fun bindUsuarioRepository(impl: FirestoreUsuarioRepository): UsuarioRepository
}
