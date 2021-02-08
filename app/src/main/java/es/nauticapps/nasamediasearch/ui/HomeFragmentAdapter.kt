package es.nauticapps.nasamediasearch.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import es.nauticapps.nasamediasearch.R
import es.nauticapps.nasamediasearch.databinding.ItemHomeListBinding
import es.nauticapps.nasamediasearch.datalayer.NasaItem

class HomeFragmentAdapter(private var nasaItems: List<NasaItem>, private var context: Context?) : RecyclerView.Adapter<HomeFragmentAdapter.ViewHolder>() {

    lateinit var binding: ItemHomeListBinding

    class ViewHolder(binding: ItemHomeListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        binding = DataBindingUtil.inflate<ItemHomeListBinding>(LayoutInflater.from(viewGroup.context), R.layout.item_home_list, viewGroup,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val myData = nasaItems[position].data.first()
        val myImage = nasaItems[position].links.first().href

        binding.txtTitle.text = myData.title
        binding.txtCenter.text = myData.center
        binding.txtShortDescription.text = myData.description_508
        binding.txtLocation.text = myData.location
        binding.txtPhotographer.text = myData.photographer

        Picasso.with(context).load(myImage).into(binding.imageMedia)

    }


    override fun getItemCount() = nasaItems.size

    fun updateList (newList: List<NasaItem>) {
        nasaItems = newList
        notifyDataSetChanged()
    }

}