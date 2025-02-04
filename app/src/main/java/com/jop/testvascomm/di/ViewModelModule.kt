package com.jop.testvascomm.di

import com.jop.testvascomm.data.local.AppData
import com.jop.testvascomm.view.login.viewModel.LoginViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single<AppData> { AppData(get()) } // SHARED PREF

    viewModel<LoginViewModel> { LoginViewModel(get(), get()) }
}