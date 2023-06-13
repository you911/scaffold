package tech.wcw.simple.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url
import tech.wcw.simple.model.NewsResult

interface ApiService {
    @GET("toutiao/index")
    suspend fun news(
        @Query("type") type: String,
        @Query("page") page: Int,
        @Query("key") key: String = "b5f53b06e299858467c008334604cf8b"
    ): NewsResult

    @Streaming
    @GET
    suspend fun download(@Url url: String): ResponseBody
}