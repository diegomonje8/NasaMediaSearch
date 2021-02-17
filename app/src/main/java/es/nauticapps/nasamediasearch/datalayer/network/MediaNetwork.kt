package es.nauticapps.nasamediasearch.datalayer.network

import es.nauticapps.nasamediasearch.datalayer.MediaModel
import es.nauticapps.nasamediasearch.datalayer.NasaCollection
import es.nauticapps.nasamediasearch.datalayer.NasaItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MediaNetwork {

    private lateinit var service : MediaService

    // https://images-api.nasa.gov//search?q=sun


    private fun createRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://images-api.nasa.gov//")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(MediaService::class.java)
    }

    suspend fun requestMediaSearch(searchText: String): MediaModel {

        createRetrofit()
        return service.getSearchItems(searchText)

    }

}