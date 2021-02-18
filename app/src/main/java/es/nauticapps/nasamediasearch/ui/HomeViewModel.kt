package es.nauticapps.nasamediasearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.nauticapps.nasamediasearch.base.BaseState
import es.nauticapps.nasamediasearch.datalayer.MediaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception
import java.net.UnknownHostException

class HomeViewModel: ViewModel() {

    //Option 1 LIVE DATA
    //val response : MutableLiveData<List<NasaItem>> by lazy {
    //    MutableLiveData<List<NasaItem>>()
    //}

    //Option 2 LIVE DATA: Improve view cannot access our viewmodel var
    //val _response : MutableLiveData<List<NasaItem>> = MutableLiveData()
    //val response: LiveData<List<NasaItem>> = _response

    //Option 3 LIVE DATA: as Option 2 easiest as a function
    private val state = MutableLiveData<BaseState>()
    fun getState() : LiveData<BaseState> = state

    fun requestMedia(searchText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var text = searchText
                if (text.isEmpty()) text = "sun"
                state.postValue(BaseState.Loading())
                val listResult = MediaRepository().requestMediaSearch(text)
                state.postValue(BaseState.Normal(HomeListState(listResult)))
            }catch(e: Exception ) {
                state.postValue(BaseState.Error(e))
            }
        }
    }
}

