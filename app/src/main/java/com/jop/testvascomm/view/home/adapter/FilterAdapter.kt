package com.jop.testvascomm.view.home.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.jop.testvascomm.R
import com.jop.testvascomm.databinding.ItemLayoutFilterBinding
import com.jop.testvascomm.ui.BaseRcAdapter

class FilterAdapter(private val listener: FilterAdapterListener): BaseRcAdapter<String, FilterAdapter.ViewHolder>() {
    private var filter: MutableList<String> = mutableListOf()
    private var selectedFilter: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    class ViewHolder(private val binding: ItemLayoutFilterBinding) : BaseRcAdapter.ViewHolder<Any>(binding.root) {
        fun bind(item: String, selected: String, listener: FilterAdapterListener) {
            binding.apply {
                val typefaceNormal: Typeface? = ResourcesCompat.getFont(root.context, R.font.new_poppins_regular)
                val typefaceMedium: Typeface? = ResourcesCompat.getFont(root.context, R.font.new_poppins_medium)

                if(selected == item){
                    root.setTextColor(root.context.getColor(R.color.white))
                    root.backgroundTintList = root.context.getColorStateList(R.color.color_primary)
                    root.typeface = typefaceMedium
                } else {
                    root.setTextColor(root.context.getColor(R.color.color_primary))
                    root.backgroundTintList = root.context.getColorStateList(R.color.white)
                    root.typeface = typefaceNormal
                }

                root.text = item
                root.setOnClickListener { listener.onClickFilter(item) }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            val filter: String? = getItem(position)
            holder.item = filter
            holder.bind(filter!!, selectedFilter, listener)
        }
    }

    override fun setData(list: MutableList<String>?) {
        this.filter = list!!
        super.setData(list)
    }

    fun getDataOriginal(): MutableList<String> {
        return this.filter
    }

    fun setSelectedFilter(selected: String) {
        this.selectedFilter = selected
        notifyDataSetChanged()
    }

    interface FilterAdapterListener{
        fun onClickFilter(filter: String)
    }
}