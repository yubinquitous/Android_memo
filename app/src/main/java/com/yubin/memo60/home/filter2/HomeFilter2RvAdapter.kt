package com.yubin.memo60.home.filter2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yubin.memo60.R
import com.yubin.memo60.db_memo.Memo

class HomeFilter2RvAdapter (private val context: Context): RecyclerView.Adapter<HomeFilter2RvAdapter.ViewHolder>() {
    val items = ArrayList<Memo>()
    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener{
        fun onClick(view: View, position: Int, memo: Memo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_memo_sticker, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position, items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItems(items: ArrayList<Memo>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        this.items.removeAt(position)
        notifyDataSetChanged()
    }

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    inner class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        private val tvTitle: TextView = itemView?.findViewById(R.id.item_memo_st_title_tv)!!
        private val tvContent: TextView = itemView?.findViewById(R.id.item_memo_st_content_tv)!!

        fun bind(item: Memo){
            tvTitle.text = item.title
            tvContent.text = item.content
        }
    }


}