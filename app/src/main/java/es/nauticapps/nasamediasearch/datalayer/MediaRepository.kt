package es.nauticapps.nasamediasearch.datalayer

import es.nauticapps.nasamediasearch.datalayer.network.MediaNetwork

class MediaRepository {

    fun requestMediaSearch(): List<NasaItem> {
        return MediaNetwork().requestMediaSearch()
    }

}