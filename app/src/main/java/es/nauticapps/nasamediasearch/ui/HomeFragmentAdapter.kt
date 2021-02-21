package es.nauticapps.nasamediasearch.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import es.nauticapps.nasamediasearch.R
import es.nauticapps.nasamediasearch.databinding.ItemHomeListBinding
import es.nauticapps.nasamediasearch.datalayer.NasaItem

class HomeFragmentAdapter(private var nasaItems: List<NasaItem>, private var context: Context?, private var listener: (nasaItem: NasaItem) -> Unit) : RecyclerView.Adapter<HomeFragmentAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemHomeListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(ItemHomeListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

         var myImage = "https://images-assets.nasa.gov/image/PIA17669/PIA17669~thumb.jpg"
         try  {
             myImage = nasaItems[position].links.firstOrNull()?.href ?: "https://images-assets.nasa.gov/image/PIA17669/PIA17669~thumb.jpg"
         } catch(e: Exception ) {
            Log.e ("NasaMedia Error", "Error catching image")
         }



        viewHolder.binding.txtTitle.text = nasaItems[position].data.firstOrNull()?.title ?: ""
        viewHolder.binding.txtCenter.text = nasaItems[position].data.firstOrNull()?.center ?: "N/A"
        viewHolder.binding.txtShortDescription.text = nasaItems[position].data.firstOrNull()?.description_508 ?: "Description Not Available"
        viewHolder.binding.txtLocation.text = nasaItems[position].data.firstOrNull()?.location ?: "N/A"
        viewHolder.binding.txtPhotographer.text = nasaItems[position].data.firstOrNull()?.photographer ?: "N/A"

        Picasso.get()
            .load(myImage)
            .resize(120,120)
            .centerCrop()
            .placeholder(R.drawable.ic_mars)
            .into(viewHolder.binding.imageMedia)

        viewHolder.itemView.setOnClickListener( View.OnClickListener {
            listener.invoke(nasaItems[position])
        })

    }


    override fun getItemCount() = nasaItems.size

    fun updateList (newList: List<NasaItem>) {
        nasaItems = newList
        notifyDataSetChanged()
    }

}