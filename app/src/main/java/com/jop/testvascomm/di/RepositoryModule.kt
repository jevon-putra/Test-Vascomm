package com.jop.testvascomm.di

import com.jop.testvascomm.data.repository.AuthRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepository(get()) }
}