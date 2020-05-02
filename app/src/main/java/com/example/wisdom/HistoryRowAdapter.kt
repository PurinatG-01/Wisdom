package com.example.wisdom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.history_row.view.*

class HistoryRowAdapter(val list_row: ArrayList<HistoryRow>, val context: Context) :
    RecyclerView.Adapter<HistoryRowAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bindItems(item: HistoryRow){
            itemView.historyNameView.text = item.event_name
            itemView.historyCardDonatedView.text = item.total_donated
            itemView.historyDateView.text = item.date
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.history_row, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list_row.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list_row[position])
    }
}