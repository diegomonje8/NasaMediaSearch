package es.nauticapps.nasamediasearch.base

import java.io.Serializable

sealed class BaseState {

    data class Normal (val data: Serializable) : BaseState()

    data class Error (val dataError: Throwable) : BaseState()

    data class Loading (val dataLoading: Serializable) : BaseState()

}


data class BaseExtraData (val type: Int, val extraData: Any? = null) : Serializable