package uz.revolution.databasebootcampnews.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.tab_item.view.*
import uz.revolution.databasebootcampnews.R
import uz.revolution.databasebootcampnews.adapters.ViewPagerAdapter
import uz.revolution.databasebootcampnews.daos.BootcampDao
import uz.revolution.databasebootcampnews.database.AppDatabase
import uz.revolution.databasebootcampnews.dialogs.AddDialog
import uz.revolution.databasebootcampnews.models.Bootcamp
import uz.revolution.databasebootcampnews.models.Category

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        database = AppDatabase.get.getDatabase()
        bootcampDao = database!!.getBootcampDao()
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        setHasOptionsMenu(true)
    }


    private var database: AppDatabase? = null
    private var bootcampDao: BootcampDao? = null
    private var data: ArrayList<Bootcamp>? = null
    private var category: ArrayList<Category>? = null
    lateinit var root: View
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var pagerPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_main, container, false)
        (activity as AppCompatActivity).setSupportActionBar(root.toolbar)
        loadData()
        loadAdapters()
        setTabs()

        root.received_vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                pagerPosition = position
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        return root
    }

    private fun loadAdapters() {
        viewPagerAdapter?.setAdapter(data!!)
        root.received_vp.adapter = viewPagerAdapter
        root.tab_layout.setupWithViewPager(root.received_vp)
        viewPagerAdapter?.notifyDataSetChanged()
    }

    private fun loadData() {
        data = ArrayList()
        category = ArrayList()

//        if (bootcampDao!!.getAllBootcamp().isEmpty()) {
//            bootcampDao?.insertBootcamp(Bootcamp("Bir narsani sarlavhasi", "Bir narsani matni", 1))
//        }
        data!!.addAll(bootcampDao?.getBootcampByCategoryID(1) as ArrayList)
        data!!.addAll(bootcampDao?.getBootcampByCategoryID(2) as ArrayList)
        data!!.addAll(bootcampDao?.getBootcampByCategoryID(3) as ArrayList)
        category = bootcampDao?.getAllCategory() as ArrayList
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_btn -> {
                val beginTransaction = childFragmentManager.beginTransaction()

                val dialog = AddDialog.newInstance(category!!, "Add")
                dialog.show(beginTransaction, "add")
                dialog.setOnSaveClick(object : AddDialog.OnSaveClick {
                    override fun onClick(bootcamp: Bootcamp) {
                        bootcampDao?.insertBootcamp(bootcamp)
                        loadData()
                        loadAdapters()
                        setTabs()
                        root.received_vp.currentItem = pagerPosition
                    }
                })
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetTextI18n")
    private fun setTabs() {
        val tabCount = root.tab_layout.tabCount

        for (i in 0 until tabCount) {
            val tabView = LayoutInflater.from(root.context).inflate(R.layout.tab_item, null, false)
            val tab = root.tab_layout.getTabAt(i)
            tab?.customView = tabView
            when (i) {
                0 -> tabView.title_tv.text = "Asosiy"
                1 -> tabView.title_tv.text = "Dunyo"
                2 -> tabView.title_tv.text = "Ijtimoiy"
            }
        }
    }

}