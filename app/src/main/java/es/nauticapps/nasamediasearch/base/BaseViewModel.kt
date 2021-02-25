package es.nauticapps.nasamediasearch.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope

abstract class BaseViewModel<VS: BaseViewState> : ViewModel() {

    private var baseState : BaseState<VS>? = null
    private val observableState: MutableLiveData<BaseState<VS>> = MutableLiveData()
    fun getObservableState() : LiveData<BaseState<VS>> = observableState

    /**
     * State Managment
     */

    fun updateToNormalState(viewState: VS) {
        baseState = BaseState.Normal(viewState)
        observableState.postValue(baseState)
    }

    fun updateToLoadingState(viewState: VS, loadingData: BaseExtraData? = null) {
        baseState = BaseState.Loading(viewState, loadingData)
        observableState.postValue(baseState)
    }

    fun updateToErrorState(viewState: VS, errorData: Throwable = Throwable()) {
        baseState = BaseState.Error(viewState, errorData)
        observableState.postValue(baseState)
    }




    fun executeCoRoutine(
        block: suspend CoroutineScope() -> Unit,
    exceptionBlock: suspend Coroutine
    ) {

    }


}