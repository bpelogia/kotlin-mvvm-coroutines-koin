package com.maiconhellmann.architecture.data.remote.endpoint

import com.maiconhellmann.architecture.BuildConfig
import com.maiconhellmann.architecture.data.remote.dto.SearchMovieDto
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieWebService {

    @GET("?${BuildConfig.API_KEY}")
    fun getMoviesAsync(@Query("s") query: String, @Query("type") type: String): Deferred<SearchMovieDto>
}