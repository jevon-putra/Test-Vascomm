package com.jop.testvascomm.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jop.testvascomm.data.model.Product
import com.jop.testvascomm.databinding.ItemLayoutProductBinding
import com.jop.testvascomm.ui.BaseRcAdapter
import com.jop.testvascomm.utils.numToRp

class ProductAdapter(private val listener: ProductAdapterListener): BaseRcAdapter<Product, ProductAdapter.ViewHolder>() {
    private var product: MutableList<Product> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    class ViewHolder(private val binding: ItemLayoutProductBinding) : BaseRcAdapter.ViewHolder<Any>(binding.root) {
        fun bind(item: Product, listener: ProductAdapterListener) {
            binding.apply {
                tvItemName.text = item.productName
                tvPrice.text = item.price.numToRp()
                tvStar.text = item.star.toString()

                root.setOnClickListener { listener.onClickProduct(item) }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            val product: Product? = getItem(position)
            holder.item = product
            holder.bind(product!!, listener)
        }
    }

    override fun setData(list: MutableList<Product>?) {
        this.product = list!!
        super.setData(list)
    }

   interface ProductAdapterListener{
       fun onClickProduct(product: Product)
   }
}