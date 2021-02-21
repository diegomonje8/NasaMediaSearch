package es.nauticapps.nasamediasearch.ui.detail

import android.app.SearchManager
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import es.nauticapps.nasamediasearch.R
import es.nauticapps.nasamediasearch.base.prettyDate
import es.nauticapps.nasamediasearch.databinding.FragmentDescriptionBinding
import es.nauticapps.nasamediasearch.datalayer.NasaItem


class DescriptionFragment : Fragment() {


    lateinit var binding: FragmentDescriptionBinding
    val args : DescriptionFragmentArgs by navArgs()
    var searchedText : String = "sun"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        binding = FragmentDescriptionBinding.inflate(inflater, container, false)

        setUpView(args.nasaItem)
        searchedText = args.searchText
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate( DescriptionFragmentDirections.actionDescriptionFragmentToHomeFragment2(searchedText))
        }

        return binding.root

        //
    }

    private fun setUpView(nasaItem: NasaItem) {

        binding.descTitle.text = nasaItem.data.firstOrNull()?.title ?: ""
        binding.descDate.text = nasaItem.data.firstOrNull()?.date_created?.prettyDate() ?: ""
        binding.descPhoto.text = nasaItem.data.firstOrNull()?.photographer ?: getString(R.string.not_available)
        binding.descLong.text = when {
            !nasaItem.data.firstOrNull()?.description.isNullOrEmpty() -> nasaItem.data.firstOrNull()?.description
            !nasaItem.data.firstOrNull()?.description_508.isNullOrEmpty() -> nasaItem.data.firstOrNull()?.description_508
            else -> "Description Not Available"
        }

        var myImage = "https://images-assets.nasa.gov/image/PIA17669/PIA17669~thumb.jpg"
        try  { myImage = nasaItem.links.firstOrNull()?.href ?: "https://images-assets.nasa.gov/image/PIA17669/PIA17669~thumb.jpg"
        } catch (e: Exception) { Log.e("NasaMedia Error", "Error catching image") }


        Picasso.get()
            .load(myImage)
            .resize(getScreenWidth(),750)
            .centerCrop()
            .placeholder(R.drawable.ic_mars)
            .into(binding.descMainImage)

        binding.descUrlBtn.setOnClickListener(View.OnClickListener {
            searchWeb(getString(R.string.nasa_url))
        })

    }

    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    private fun searchWeb(query: String) {
        val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
            putExtra(SearchManager.QUERY, query)
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }
}



