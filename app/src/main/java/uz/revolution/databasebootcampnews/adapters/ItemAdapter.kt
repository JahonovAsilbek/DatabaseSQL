package uz.revolution.databasebootcampnews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import uz.revolution.databasebootcampnews.databinding.ItemBootcampBinding
import uz.revolution.databasebootcampnews.models.Bootcamp

class ItemAdapter() : RecyclerView.Adapter<ItemAdapter.VH>() {

    private var onMenuClick:OnMenuClick?=null
    private var onItemClick:OnItemClick?=null
    private var data: ArrayList<Bootcamp>?=null

    fun setAdapter(data: ArrayList<Bootcamp>) {
        this.data=data
    }

    inner class VH(var itemBootcampBinding: ItemBootcampBinding) :
        RecyclerView.ViewHolder(itemBootcampBinding.root) {

        fun onBind(bootcamp: Bootcamp,position: Int) {
            itemBootcampBinding.title.text=bootcamp.title
            itemBootcampBinding.text.text=bootcamp.text

            itemBootcampBinding.popupMenu.setOnClickListener {
                if (onMenuClick != null) {
                    onMenuClick!!.onClick(bootcamp,position,itemBootcampBinding.popupMenu)
                }
            }

            itemBootcampBinding.root.setOnClickListener{
                if (onItemClick != null) {
                    onItemClick!!.onClick(bootcamp, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemBootcampBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(data!![position],position)
    }

    override fun getItemCount(): Int = data!!.size

    interface OnMenuClick{
        fun onClick(bootcamp: Bootcamp, position: Int, imageView: ImageView)
    }

    fun setOnMenuClick(onMenuClick: OnMenuClick) {
        this.onMenuClick=onMenuClick
    }

    interface OnItemClick{
        fun onClick(bootcamp: Bootcamp,position: Int)
    }

    fun setOnItemClick(onItemClick: OnItemClick) {
        this.onItemClick=onItemClick
    }
}