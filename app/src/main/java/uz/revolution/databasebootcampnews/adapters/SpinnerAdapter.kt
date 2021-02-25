package uz.revolution.databasebootcampnews.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.item_spinner.view.*
import uz.revolution.databasebootcampnews.R
import uz.revolution.databasebootcampnews.models.Category
import java.util.*

class SpinnerAdapter(var list: ArrayList<Category>) : BaseAdapter() {

    override fun getCount(): Int = list.size

    override fun getItem(p0: Int): Category {
        return list[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view =
            LayoutInflater.from(parent?.context).inflate(R.layout.item_spinner, parent, false)

        view.spinner_tv.text = list[position].name

        return view
    }
}