package es.nauticapps.nasamediasearch.ui

import es.nauticapps.nasamediasearch.base.BaseViewState
import es.nauticapps.nasamediasearch.datalayer.NasaItem
import java.io.Serializable

data class HomeListState (

    val listMediaNasa : List<NasaItem> = listOf()

):BaseViewState()