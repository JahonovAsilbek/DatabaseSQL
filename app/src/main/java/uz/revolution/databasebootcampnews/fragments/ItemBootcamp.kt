package uz.revolution.databasebootcampnews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_item_bootcamp.view.*
import uz.revolution.databasebootcampnews.R
import uz.revolution.databasebootcampnews.daos.BootcampDao
import uz.revolution.databasebootcampnews.database.AppDatabase
import uz.revolution.databasebootcampnews.databinding.FragmentItemBootcampBinding
import uz.revolution.databasebootcampnews.dialogs.EditDialog
import uz.revolution.databasebootcampnews.models.Bootcamp


private const val ARG_PARAM1 = "item"
private const val ARG_PARAM2 = "position"

class ItemBootcamp : Fragment() {

    private var bootcamp: Bootcamp? = null
    private var param2: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bootcamp = it.getSerializable(ARG_PARAM1) as Bootcamp
            param2 = it.getInt(ARG_PARAM2)
        }
        database = AppDatabase.get.getDatabase()
        bootcampDao = database!!.getBootcampDao()
    }

    lateinit var root: View
    private var database: AppDatabase? = null
    private var bootcampDao: BootcampDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = FragmentItemBootcampBinding.inflate(layoutInflater).root
        (activity as AppCompatActivity).setSupportActionBar(root.toolbar_item)
        (activity as AppCompatActivity).supportActionBar.apply {
            this!!.setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrowleft)
        }
        loadDataToView()
        floatClick()

        return root
    }

    private fun floatClick() {
        val bootcampID=bootcamp?.id
        root.floatactionbutton.setOnClickListener {
            val beginTransaction = childFragmentManager.beginTransaction()
            val editDialog =
                EditDialog.newInstance(
                    bootcamp!!,
                    bootcampDao?.getAllCategory() as ArrayList
                )
            editDialog.show(beginTransaction, "editDialog")

            editDialog.setOnSaveClick(object : EditDialog.OnSaveClick {
                override fun onClick(bootcamp: Bootcamp) {
                    bootcampDao?.updateBootcamp(
                        bootcamp.title!!,
                        bootcamp.text!!, bootcamp.categoryID!!, bootcampID!!
                    )
                    findNavController().popBackStack()
                }
            })
        }
    }

    private fun loadDataToView() {
        root.title_item.text = bootcamp?.title
        root.text_item.text = bootcamp?.text
    }

    companion object {

        @JvmStatic
        fun newInstance(bootcamp: Bootcamp, param2: Int) =
            ItemBootcamp().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, bootcamp)
                    putSerializable(ARG_PARAM2, param2)
                }
            }
    }
}