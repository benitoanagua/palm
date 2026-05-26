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
    abstract fun bindRutaRepository(impl: FirestoreRutaRepository): RutaRepository

    @Binds @Singleton
    abstract fun bindEnvioRepository(impl: FirestoreEnvioRepository): EnvioRepository

    @Binds @Singleton
    abstract fun bindTransportistaRepository(impl: FirestoreTransportistaRepository): TransportistaRepository

    @Binds @Singleton
    abstract fun bindUsuarioRepository(impl: FirestoreUsuarioRepository): UsuarioRepository
}
