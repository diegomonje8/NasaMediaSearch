package es.nauticapps.nasamediasearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.nauticapps.nasamediasearch.datalayer.MediaRepository
import es.nauticapps.nasamediasearch.datalayer.NasaItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel: ViewModel() {

    //Option 1 LIVE DATA
    //val response : MutableLiveData<List<NasaItem>> by lazy {
    //    MutableLiveData<List<NasaItem>>()
    //}

    //Option 2 LIVE DATA: Improve view cannot access our viewmodel var
    //val _response : MutableLiveData<List<NasaItem>> = MutableLiveData()
    //val response: LiveData<List<NasaItem>> = _response

    //Option 3 LIVE DATA: as Option 2 easiest as a function
    private val response = MutableLiveData<List<NasaItem>>()
    fun getResponse() : LiveData<List<NasaItem>> = response

    val isError : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    init {
        isError.postValue(false)
    }

    fun requestMedia(searchText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var text = searchText
                if (text.isEmpty()) text = "sun"
                val listResult = MediaRepository().requestMediaSearch(text)
                response.postValue(listResult)
            }catch(e: Exception ) {
                isError.postValue(true)
            }finally {
                //finally event
            }
        }

    }


}


