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

    fun requestMediaSearch(): List<NasaItem> {

        createRetrofit()
        //val request: Call<NasaItem> = service.getSearchItems("sun")
        val request: Call<MediaModel> = service.getMockItems()
        val listResult = request.execute().body()
        return listResult?.collection?.items ?: listOf()

    }

}