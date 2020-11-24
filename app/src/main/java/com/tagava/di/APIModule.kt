package  com.tagava.di

import com.tagava.repository.ArticleDataSource
import com.tagava.repository.ArticleDataSourceFactory
import com.tagava.repository.RetrofitRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class APIModule constructor(baseURL: String) {
    var baseURL: String? = ""

    init {
        this.baseURL = baseURL
    }

    @Singleton
    @Provides
    fun provideOKHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(2000, TimeUnit.SECONDS)
            .connectTimeout(2000, TimeUnit.SECONDS)
            .build()

    }

    @Singleton
    @Provides
    fun provideGSON(): GsonConverterFactory {

        return GsonConverterFactory.create()

    }

    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideRetroRepository(): RetrofitRepository {
        return RetrofitRepository()
    }

    @Provides
    fun provideArticlDataSource(): ArticleDataSource {
        return ArticleDataSource()
    }

    @Provides
    fun provideArticleDataSourceFactory(): ArticleDataSourceFactory {
        return ArticleDataSourceFactory()
    }

}
