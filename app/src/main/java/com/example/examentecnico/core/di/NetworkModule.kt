package com.example.examentecnico.core.di

/*@Module
    @InstallIn(SingletonComponent::class)
    class NetworkModule {

        @Singleton
        @Provides
        fun provideRetrofit(): Retrofit{
            return Retrofit.Builder()
                .baseUrl("https://demo2900457.mockable.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Singleton
        @Provides
        fun provideRecipeClient(retrofit: Retrofit): RecipeClient{
            return retrofit.create(RecipeClient::class.java)
        }
    }*/