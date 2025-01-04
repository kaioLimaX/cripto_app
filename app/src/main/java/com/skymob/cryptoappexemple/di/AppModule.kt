package com.skymob.cryptoappexemple.di

import androidx.room.Room
import com.google.gson.Gson
import com.skymob.cryptoappexemple.data.local.AppDatabase
import com.skymob.cryptoappexemple.data.local.repository.LocalCryptoRepositoryImpl
import com.skymob.cryptoappexemple.data.remote.api.ApiService
import com.skymob.cryptoappexemple.data.remote.api.interceptor.ApiKeyInterceptor
import com.skymob.cryptoappexemple.data.remote.repository.RemoteCryptoRepositoryImpl
import com.skymob.cryptoappexemple.data.remote.util.NetworkHelper
import com.skymob.cryptoappexemple.domain.local.repository.LocalCryptoRepository
import com.skymob.cryptoappexemple.domain.local.usecases.CheckFavoriteUseCase
import com.skymob.cryptoappexemple.domain.local.usecases.GetCryptoFavoritesUseCase
import com.skymob.cryptoappexemple.domain.local.usecases.InsertCryptoFavoriteUseCase
import com.skymob.cryptoappexemple.domain.local.usecases.RemoveCryptoFavoriteUseCase
import com.skymob.cryptoappexemple.domain.remote.repository.RemoteCryptoRepository
import com.skymob.cryptoappexemple.domain.remote.usecases.GetCryptoInfoUseCase
import com.skymob.cryptoappexemple.domain.remote.usecases.GetCryptoListsUseCase
import com.skymob.cryptoappexemple.domain.remote.usecases.SearchCryptoUseCase
import com.skymob.cryptoappexemple.presentation.ui.details.DetailsViewModel
import com.skymob.cryptoappexemple.presentation.ui.main.fragments.favorite.FavoriteViewModel
import com.skymob.cryptoappexemple.presentation.ui.main.fragments.home.HomeViewModel
import com.skymob.cryptoappexemple.presentation.ui.main.fragments.search.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {

    single { Gson() }

    single { NetworkHelper(get()) }

    single { ApiKeyInterceptor() }

    single { provideOkHttpClient(get()) }

    single { provideRetrofit(get()) }

    single { get<Retrofit>().create(ApiService::class.java) }
}



val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "crypto_database"
        ).fallbackToDestructiveMigration()
            .build()
    }


    single { get<AppDatabase>().favoriteCryptoDao() }

}

val repositoryModule = module {
    //remoto
    single<RemoteCryptoRepository> { RemoteCryptoRepositoryImpl(get(), get()) }
    //local
    single<LocalCryptoRepository> { LocalCryptoRepositoryImpl(get()) }
}

val useCaseModule = module {

    // Casos de uso remotos
    factory { GetCryptoListsUseCase(get()) }
    factory { GetCryptoInfoUseCase(get()) }
    factory { SearchCryptoUseCase(get()) }

    // Casos de uso locais
    factory { GetCryptoFavoritesUseCase(get()) }
    factory { InsertCryptoFavoriteUseCase(get()) }
    factory { RemoveCryptoFavoriteUseCase(get()) }
    factory { CheckFavoriteUseCase(get()) }
}

val viewModelModule = module {

    viewModel { HomeViewModel(get()) }

    viewModel { FavoriteViewModel(get(),get()) }

    viewModel { DetailsViewModel(get(),get(),get()) }

    viewModel { SearchViewModel(get()) }
}



