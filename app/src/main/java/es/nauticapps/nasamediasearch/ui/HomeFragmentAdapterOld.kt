package es.nauticapps.nasamediasearch.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import es.nauticapps.nasamediasearch.R
import es.nauticapps.nasamediasearch.datalayer.NasaItem


class HomeFragmentAdapterOld(private var nasaItems: List<NasaItem>, private val context: Context?) : RecyclerView.Adapter<HomeFragmentAdapterOld.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textTitle : TextView = view.findViewById(R.id.txtTitle)
        val textDesc : TextView = view.findViewById(R.id.txtShortDescription)
        val textPhoto : TextView = view.findViewById(R.id.txtPhotographer)
        var textCenter : TextView = view.findViewById(R.id.txtCenter)
        val textLocation : TextView = view.findViewById(R.id.txtLocation)
        val imageMedia : ImageView = view.findViewById(R.id.imageMedia)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_home_list, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val myData = nasaItems[position].data.first()
        val myImage = nasaItems[position].links.first().href

        viewHolder.textTitle.text = myData.title
        viewHolder.textDesc.text = myData.description_508
        viewHolder.textPhoto.text = myData.photographer
        viewHolder.textCenter.text = myData.center
        viewHolder.textLocation.text = myData.location
        Picasso.with(context)
            .load(myImage)
            .into(viewHolder.imageMedia);

    }



    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = nasaItems.size

    fun updateList (newList: List<NasaItem>) {
        nasaItems = newList
        notifyDataSetChanged()
    }

}