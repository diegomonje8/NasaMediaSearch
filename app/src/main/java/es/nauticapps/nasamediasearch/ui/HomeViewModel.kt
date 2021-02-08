package es.nauticapps.nasamediasearch.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.nauticapps.nasamediasearch.datalayer.MediaRepository
import es.nauticapps.nasamediasearch.datalayer.NasaItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel: ViewModel() {


    val response : MutableLiveData<List<NasaItem>> by lazy {
        MutableLiveData<List<NasaItem>>()
    }

    val isError : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    init {
        isError.postValue(false)
    }

    fun requestMedia() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val listResult = MediaRepository().requestMediaSearch()
                response.postValue(listResult)
            }catch(e: Exception ) {
                isError.postValue(true)
            }finally {
                //finally event
            }
        }

    }


}


