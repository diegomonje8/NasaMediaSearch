package es.nauticapps.nasamediasearch.datalayer.network

import es.nauticapps.nasamediasearch.datalayer.MediaModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface MediaService {



    @GET("search?q=sun")
    fun getMockItems(): Call<MediaModel>

    @GET("search/q={searchText}")
    fun getSearchItems(@Path("searchText") searchText: String): Call<MediaModel>

}