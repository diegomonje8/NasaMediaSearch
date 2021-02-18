package es.nauticapps.nasamediasearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import es.nauticapps.nasamediasearch.base.BaseState
import es.nauticapps.nasamediasearch.base.hideKeyboard
import es.nauticapps.nasamediasearch.databinding.FragmentHomeBinding
import es.nauticapps.nasamediasearch.datalayer.NasaItem
import retrofit2.HttpException
import java.net.UnknownHostException


class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    lateinit var binding : FragmentHomeBinding
    lateinit var myAdapter: HomeFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.getState().observe(viewLifecycleOwner)  { state ->
            when(state) {
                is BaseState.Loading -> {
                    binding.fragmentHomeProgressBar.visibility = View.VISIBLE
                }
                is BaseState.Normal -> {
                    binding.fragmentHomeProgressBar.visibility = View.GONE
                    myAdapter.updateList((state.data as HomeListState).listMediaNasa)
                }
                is BaseState.Error -> {
                    myAdapter.updateList(listOf())

                    val msg = when (state.dataError) {
                        is HttpException -> "Network Error: " + state.dataError.code().toString()
                        is UnknownHostException -> "Please, Internet connection needed !!"
                        else -> "Oops Unknown Error, Please try later !!"
                    }

                    MaterialAlertDialogBuilder(requireActivity())
                        .setTitle("Error")
                        .setMessage(msg)
                        .setPositiveButton("Retru") { _, _ ->
                            viewModel.requestMedia("sun")
                        }
                        .show()
                }
            }
        }

        myAdapter = HomeFragmentAdapter(listOf<NasaItem>(), context)
        val myRecyclerView : RecyclerView = binding.myRecyclerList
        myRecyclerView.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
        }



        viewModel.requestMedia(searchText = "")

        binding.outlinedTextField.setEndIconOnClickListener(View.OnClickListener {
            viewModel.requestMedia(binding.outlinedTextField.editText?.text.toString())
            hideKeyboard()
        })


        return binding.root
    }




}