package uz.revolution.databasebootcampnews.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import uz.revolution.databasebootcampnews.fragments.ItemFragment
import uz.revolution.databasebootcampnews.models.Bootcamp

class ViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(
        fragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {

    private var data: ArrayList<Bootcamp>? = null

    fun setAdapter(data: ArrayList<Bootcamp>) {
        this.data = data
    }

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {

        val bundle = Bundle()
        bundle.putInt("categoryID", position + 1)
        val fragment = ItemFragment()
        fragment.arguments = bundle
        return fragment
    }
}