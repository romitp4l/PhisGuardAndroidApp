package com.example.phisguardandroidapp





import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(private val context: Context, private val urlList: ArrayList<String>) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.url_item, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val url = urlList[position]
        holder.tvUrl.text = url

        // Set click listener for the "Test link for phishing" button
        holder.btnTestPhishing.setOnClickListener {
            val intent = Intent(context, ResultDashboardScreenActivity::class.java).apply {
                putExtra("url", url)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return urlList.size
    }

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUrl: TextView = itemView.findViewById(R.id.tvUrl)
        val btnTestPhishing: Button = itemView.findViewById(R.id.btnTestPhishing)
    }
}
