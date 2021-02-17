package es.nauticapps.nasamediasearch.datalayer.network

import es.nauticapps.nasamediasearch.datalayer.MediaModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MediaService {

    @GET("search?q=sun")
    suspend fun getMockItems(): MediaModel

    @GET("search")
    suspend fun getSearchItems(@Query("q") searchText: String): MediaModel
    //NOTA, coroutine full thread auto management
}