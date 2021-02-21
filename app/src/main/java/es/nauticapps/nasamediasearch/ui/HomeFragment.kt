package es.nauticapps.nasamediasearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.nauticapps.nasamediasearch.MainActivity
import es.nauticapps.nasamediasearch.base.BaseState
import es.nauticapps.nasamediasearch.base.NoInternetConnectivity
import es.nauticapps.nasamediasearch.base.hideKeyboard
import es.nauticapps.nasamediasearch.databinding.FragmentHomeBinding
import es.nauticapps.nasamediasearch.datalayer.NasaItem
import es.nauticapps.nasamediasearch.ui.detail.DescriptionFragmentArgs
import retrofit2.HttpException
import java.net.UnknownHostException




class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    lateinit var binding : FragmentHomeBinding
    lateinit var myAdapter: HomeFragmentAdapter
    private var searchedText : String = "sun"
    val args : HomeFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.getState().observe(viewLifecycleOwner)  { state ->
            when(state) {
                is BaseState.Loading -> updateToLoadinglState()
                is BaseState.Normal -> updateToNormalState(state.data as HomeListState)
                is BaseState.Error -> updateToErrorState(state.dataError)
            }
        }

        setupView()

        searchedText = args.searchedText
        viewModel.requestMedia(searchText = args.searchedText)

        return binding.root
    }

    /**
     *  Set Up View
     */
    private fun setupView() {

        myAdapter = HomeFragmentAdapter(listOf<NasaItem>(), context) { nasaItem ->
            findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToDescriptionFragment(nasaItem, searchedText))
        }

        val myRecyclerView : RecyclerView = binding.myRecyclerList
        myRecyclerView.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
        }

        binding.outlinedTextField.setEndIconOnClickListener(View.OnClickListener {
            searchedText = binding.outlinedTextField.editText?.text.toString()
            viewModel.requestMedia(searchedText)
            hideKeyboard()
        })


    }

    /**
     * Normal State Actions
     */
    private fun updateToNormalState(data: HomeListState) {
        binding.fragmentHomeProgressBar.visibility = View.GONE
        myAdapter.updateList((data).listMediaNasa)
    }

    /**
     * Loading State Actions
     */
    private fun updateToLoadinglState() {
        binding.fragmentHomeProgressBar.visibility = View.VISIBLE
    }

    /**
     * Error State Actions
     */
    private fun updateToErrorState(dataError : Throwable) {
        binding.fragmentHomeProgressBar.visibility = View.GONE
        myAdapter.updateList(listOf())

        val msg = when (dataError) {
            is HttpException -> "Network Error: " + dataError.code().toString()
            is UnknownHostException -> "Please, Internet connection needed !!"
            is NoInternetConnectivity -> "Connect your data or wifi and try again !!"
            else -> "Oops Unknown Error, Please try later !!"
        }

        Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show();

        /* BETTER TOAST
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Error")
            .setMessage(msg)
            .setPositiveButton("Retry") { _, _ ->
                viewModel.requestMedia("sun")
            }
            .show()
         */

    }




}