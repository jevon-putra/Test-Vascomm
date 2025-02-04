package com.jop.testvascomm.view.home.adapter

import android.content.Context
import android.os.Build
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jop.testvascomm.R
import com.jop.testvascomm.data.model.MenuEvent
import com.jop.testvascomm.databinding.ItemLayoutEventImageLeftBinding
import com.jop.testvascomm.databinding.ItemLayoutEventImageRightBinding

private const val VIEW_ICON_LEFT = 1
private const val VIEW_ICON_RIGHT = 2

class MenuEventAdapter(private val context: Context, private val listener: MenuEventAdapterListener): RecyclerView.Adapter<MenuEventAdapter.MenuEventViewHolder>(){
    private var menuEvents: MutableList<MenuEvent> = mutableListOf()

    open class MenuEventViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        open fun bind(menuEvent: MenuEvent, listener: MenuEventAdapterListener) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuEventViewHolder {
        return if(viewType == VIEW_ICON_LEFT){
            MenuEventLeftViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout_event_image_left, parent, false))
        } else {
            MenuEventRightViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout_event_image_right, parent, false))
        }
    }

    inner class  MenuEventLeftViewHolder(view: View): MenuEventViewHolder(view){
        private val binding = ItemLayoutEventImageLeftBinding.bind(view)
        private val holder = binding.holder
        private val tvTitle = binding.tvTitle
        private val tvMessage = binding.tvMessage
        private val ivIllustration = binding.ivIllustration
        private val lnAction = binding.lnAction
        private val tvActionTitle = binding.tvActionTitle

        override fun bind(menuEvent: MenuEvent, listener: MenuEventAdapterListener) {
            tvTitle.text = Html.fromHtml(menuEvent.title, Html.FROM_HTML_MODE_COMPACT)
            tvTitle.text = menuEvent.title
            tvMessage.text = menuEvent.message
            tvActionTitle.text = menuEvent.textButton
            ivIllustration.setImageResource(menuEvent.illustration)
            holder.setBackgroundResource(menuEvent.background)

            lnAction.setOnClickListener { listener.onClickMenuEvent(menuEvent) }
        }
    }

    inner class  MenuEventRightViewHolder(view: View): MenuEventViewHolder(view){
        private val binding = ItemLayoutEventImageRightBinding.bind(view)
        private val holder = binding.holder
        private val tvTitle = binding.tvTitle
        private val tvMessage = binding.tvMessage
        private val ivIllustration = binding.ivIllustration
        private val lnAction = binding.lnAction
        private val tvAction = binding.tvAction
        private val tvActionTitle = binding.tvActionTitle

        override fun bind(menuEvent: MenuEvent, listener: MenuEventAdapterListener) {
            tvTitle.text = Html.fromHtml(menuEvent.title, Html.FROM_HTML_MODE_COMPACT)
            tvMessage.text = menuEvent.message
            tvActionTitle.text = menuEvent.textButton
            tvAction.text = menuEvent.textButton

            if(menuEvent.isButton){
                tvAction.visibility = View.VISIBLE
                lnAction.visibility = View.GONE
            } else {
                tvAction.visibility = View.GONE
                lnAction.visibility = View.VISIBLE
            }

            ivIllustration.setImageResource(menuEvent.illustration)
            holder.setBackgroundResource(menuEvent.background)

            lnAction.setOnClickListener { listener.onClickMenuEvent(menuEvent) }
            tvAction.setOnClickListener { listener.onClickMenuEvent(menuEvent) }
        }
    }

    private fun getMenuEventType(menuEvent: MenuEvent): Int{
        return if(menuEvent.isIllustrationInLeft) VIEW_ICON_LEFT else VIEW_ICON_RIGHT
    }

    override fun onBindViewHolder(holder: MenuEventViewHolder, position: Int) {
        holder.apply {
            val menuEvent: MenuEvent = menuEvents[position]
            holder.bind(menuEvent!!, listener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getMenuEventType(menuEvents[position])
    }

    override fun getItemCount(): Int {
        return menuEvents.size
    }

    fun setData(list: MutableList<MenuEvent>) {
        this.menuEvents = list
        notifyDataSetChanged()
    }

    fun getDataOriginal(): MutableList<MenuEvent> {
        return this.menuEvents
    }

    interface MenuEventAdapterListener {
        fun onClickMenuEvent(menuEvent: MenuEvent)
    }
}