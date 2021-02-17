package es.nauticapps.nasamediasearch.datalayer

import es.nauticapps.nasamediasearch.datalayer.network.MediaNetwork

class MediaRepository {

    suspend fun requestMediaSearch(searchText: String): List<NasaItem> {
        return MediaNetwork().requestMediaSearch(searchText).collection.items
    }

}