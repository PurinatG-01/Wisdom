package com.example.wisdom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.history_row.view.*

// Class for creating the CardView for Recyclerview in the HistoryActivity
class HistoryRowAdapter(val list_row: ArrayList<HistoryRow>, val context: Context) :
    RecyclerView.Adapter<HistoryRowAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        // Assign value to sub view in Card View
        fun bindItems(item: HistoryRow){
            itemView.historyNameView.text = item.event_name
            itemView.historyCardDonatedView.text = item.total_donated
            itemView.historyDateView.text = item.date
        }

    }

    // Calling function for using custom Card View .xml file
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.history_row, parent, false)
        return ViewHolder(v)
    }

    // Get number of item
    override fun getItemCount(): Int {
        return list_row.size
    }

    // Bind Card View to Data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list_row[position])
    }
}