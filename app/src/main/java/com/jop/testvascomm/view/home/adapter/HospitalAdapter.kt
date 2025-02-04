package com.jop.testvascomm.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jop.testvascomm.data.model.Hospital
import com.jop.testvascomm.databinding.ItemLayoutHospitalBinding
import com.jop.testvascomm.ui.BaseRcAdapter
import com.jop.testvascomm.utils.numToRp

class HospitalAdapter(private val listener: HospitalAdapterListener): BaseRcAdapter<Hospital, HospitalAdapter.ViewHolder>() {
    private var hospitals: MutableList<Hospital> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutHospitalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    class ViewHolder(private val binding: ItemLayoutHospitalBinding) : BaseRcAdapter.ViewHolder<Any>(binding.root) {
        fun bind(item: Hospital, listener: HospitalAdapterListener) {
            binding.apply {
                tvHospitalName.text = item.hospitalName
                tvPrice.text = item.price.numToRp()
                tvAddress.text = item.address
                tvLocation.text = item.location
                ivHospital.setImageResource(item.image)

                root.setOnClickListener { listener.onClickHospital(item) }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            val hospital: Hospital? = getItem(position)
            holder.item = hospital
            holder.bind(hospital!!, listener)
        }
    }

    override fun setData(list: MutableList<Hospital>?) {
        this.hospitals = list!!
        super.setData(list)
    }

   interface HospitalAdapterListener{
       fun onClickHospital(hospital: Hospital)
   }
}