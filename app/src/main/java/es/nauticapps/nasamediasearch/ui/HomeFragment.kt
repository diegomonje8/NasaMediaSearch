package es.nauticapps.nasamediasearch.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.nauticapps.nasamediasearch.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    lateinit var binding : FragmentHomeBinding
    lateinit var myAdapter: HomeFragmentAdapterOld

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentHomeBinding.inflate(inflater, container,false)

        myAdapter = HomeFragmentAdapterOld(listOf(), context)
        val myRecyclerView : RecyclerView = binding.myRecyclerList
        myRecyclerView.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
        }

        viewModel.response.observe( viewLifecycleOwner) { response ->
            myAdapter.updateList(response)

            Log.e("Items", response[0].data.first().title)
        }
        
        viewModel.isError.observe( viewLifecycleOwner) { isError ->
            Log.e("Items", isError.toString())
        }

        viewModel.requestMedia()

        return binding.root
    }


}