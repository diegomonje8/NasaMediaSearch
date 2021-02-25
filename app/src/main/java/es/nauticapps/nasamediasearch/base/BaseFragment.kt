package es.nauticapps.nasamediasearch.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<VM: BaseViewModel, B: ViewDataBinding> : Fragment() {

    /**
    * View Model
    */
    private lateinit var viewModel: VM

    abstract val viewModelClass: Class<VM>

    /**
     * Data Binding
     */
    private lateinit var binding : B
    abstract var bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> B


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)

        binding = bindingInflater.invoke(inflater, container, false)
    }

    override fun onResume() {
        super.onResume()
        viewModel = ViewModelProvider(this).get(viewModelClass)
        setUp(viewModel)
    }

    abstract fun setUp(viewModel: VM)


}