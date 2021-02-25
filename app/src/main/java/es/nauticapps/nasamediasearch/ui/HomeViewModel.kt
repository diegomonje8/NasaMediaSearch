package es.nauticapps.nasamediasearch.ui

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.*
import es.nauticapps.nasamediasearch.base.BaseState
import es.nauticapps.nasamediasearch.base.BaseViewModel
import es.nauticapps.nasamediasearch.base.NetworkManager
import es.nauticapps.nasamediasearch.base.NoInternetConnectivity
import es.nauticapps.nasamediasearch.datalayer.MediaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.UnknownHostException

//class HomeViewModel(app: Application): AndroidViewModel(app) {
class HomeViewModel(): BaseViewModel<HomeListState>() {

    //Option 1 LIVE DATA
    //val response : MutableLiveData<List<NasaItem>> by lazy {
    //    MutableLiveData<List<NasaItem>>()
    //}

    //Option 2 LIVE DATA: Improve view cannot access our viewmodel var
    //val _response : MutableLiveData<List<NasaItem>> = MutableLiveData()
    //val response: LiveData<List<NasaItem>> = _response

    //Option 3 LIVE DATA: as Option 2 easiest as a function

    fun requestMedia(searchText: String) {

        val isNetworkAvailable = true //NetworkManager().isNetworkAvailable(getApplication()) //TO DO DEPENDENCES INJECTION

        if (isNetworkAvailable) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    var searchedText = searchText
                    if (searchedText.isEmpty()) {searchedText = "sun" }
                    state.postValue(BaseState.Loading())
                    val listResult = MediaRepository().requestMediaSearch(searchedText)
                    state.postValue(BaseState.Normal(HomeListState(listResult)))
                }catch(e: Exception ) {
                    state.postValue(BaseState.Error(e))
                }
            }
        }
        else {
            state.postValue(BaseState.Error(NoInternetConnectivity()))
        }


    }
}


