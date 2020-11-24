package com.tagava.di



import com.tagava.viewmodel.HomeViewModel
import com.tagava.viewmodel.HomeViewModelFactory
import com.tagava.repository.ArticleDataSource
import com.tagava.repository.ArticleDataSourceFactory
import com.tagava.repository.RetrofitRepository
import com.tagava.view.HomeFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, APIModule::class])
interface APIComponent {
    fun inject(retrofitRepository: RetrofitRepository)
    fun inject(homeViewModel: HomeViewModel)
    fun inject(homeFragment: HomeFragment)
    fun inject(homeViewModelFactory: HomeViewModelFactory)
    fun inject(articleDataSource: ArticleDataSource)
    fun inject(articleDataSourceFactory: ArticleDataSourceFactory)
}
