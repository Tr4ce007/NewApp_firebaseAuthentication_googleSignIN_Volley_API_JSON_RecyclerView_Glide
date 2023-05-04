package com.example.newsapp


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide



class NewsAdapter(): RecyclerView.Adapter<NewsViewHolder>() {

    private var items =ArrayList<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.title.setText(currentItem.title)
        holder.description.setText(currentItem.description)
        holder.publisher.setText(currentItem.source)
        holder.time.setText(currentItem.publishedAt)
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.img)
    }
    fun updatedNews(updatedNews:ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }

}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var cardview:CardView=itemView.findViewById(R.id.card_view)
    var title:TextView=itemView.findViewById(R.id.text_title)
    var description:TextView=itemView.findViewById(R.id.text_description)
    var publisher:TextView=itemView.findViewById(R.id.text_source)
    var time:TextView=itemView.findViewById(R.id.text_time)
    var img:ImageView=itemView.findViewById(R.id.text_img)
}