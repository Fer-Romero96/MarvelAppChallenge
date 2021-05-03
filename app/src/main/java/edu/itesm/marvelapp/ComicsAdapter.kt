package edu.itesm.marvelapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ComicsAdapter(private val data: List<Comic>?) : RecyclerView.Adapter<ComicsAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){

        fun bind(property: Comic){
            val title = view.findViewById<TextView>(R.id.tituloComic)
            val imageView = view.findViewById<ImageView>(R.id.imagenComic)
            val description = view.findViewById<TextView>(R.id.descComic)

            title.text = property.title
            description.text =  property.description
            Glide.with(view.context)
                .load(property.thumbnail.path +"." +property.thumbnail.extension)
                .override(500,500)
                .error(R.drawable.ic_launcher_background)
                .into(imageView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.comic_renglon,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data!![position])
    }

    override fun getItemCount(): Int {
        return data!!.size
    }
}