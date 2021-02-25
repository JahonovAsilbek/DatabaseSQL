package uz.revolution.databasebootcampnews.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.delete_dialog.view.*
import kotlinx.android.synthetic.main.fragment_item.view.*
import kotlinx.android.synthetic.main.item_bootcamp.view.*
import uz.revolution.databasebootcampnews.R
import uz.revolution.databasebootcampnews.adapters.ItemAdapter
import uz.revolution.databasebootcampnews.daos.BootcampDao
import uz.revolution.databasebootcampnews.database.AppDatabase
import uz.revolution.databasebootcampnews.dialogs.EditDialog
import uz.revolution.databasebootcampnews.models.Bootcamp

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "bootcamp"
private const val ARG_PARAM2 = "categoryID"

/**
 * A simple [Fragment] subclass.
 * Use the [ItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: ArrayList<Bootcamp>? = null
    private var param2: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param2 = it.getInt(ARG_PARAM2)
        }
        database = AppDatabase.get.getDatabase()
        bootcampDao = database!!.getBootcampDao()
        adapter = ItemAdapter()
    }

    lateinit var root: View
    lateinit var adapter: ItemAdapter
    private var database: AppDatabase? = null
    private var bootcampDao: BootcampDao? = null
    private var data: ArrayList<Bootcamp>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_item, container, false)
        loadData()
        loadItemAdapter()
        popupClick()
        onItemClick()

        return root
    }

    private fun onItemClick() {
        adapter.setOnItemClick(object : ItemAdapter.OnItemClick {
            override fun onClick(bootcamp: Bootcamp, position: Int) {
                val bundle = Bundle()
                bundle.putSerializable("item",bootcamp)
                bundle.putInt("position",position)
                findNavController().navigate(R.id.itemBootcamp,bundle)
            }
        })
    }

    private fun loadData() {
        data = ArrayList()
        data = bootcampDao?.getBootcampByCategoryID(param2!!) as ArrayList
    }

    private fun popupClick() {
        adapter.setOnMenuClick(object : ItemAdapter.OnMenuClick {
            override fun onClick(bootcamp: Bootcamp, position: Int, imageVIew: ImageView) {
                val bootcampID = bootcamp.id
                val wrapper: Context = ContextThemeWrapper(root.context, R.style.popupOverflowMenu)
                val popupMenu = PopupMenu(wrapper, imageVIew)
                popupMenu.inflate(R.menu.popup)

                popupMenu.setOnMenuItemClickListener {

                    when (it.itemId) {
                        R.id.edit -> {
                            // Edit Dialog Open

                            val beginTransaction = childFragmentManager.beginTransaction()
                            val editDialog =
                                EditDialog.newInstance(
                                    bootcamp,
                                    bootcampDao?.getAllCategory() as ArrayList
                                )
                            editDialog.show(beginTransaction, "editDialog")

                            editDialog.setOnSaveClick(object : EditDialog.OnSaveClick {
                                override fun onClick(bootcamp: Bootcamp) {
                                    bootcampDao?.updateBootcamp(
                                        bootcamp.title!!,
                                        bootcamp.text!!, bootcamp.categoryID!!, bootcampID!!
                                    )
                                    loadData()
                                    loadItemAdapter()
                                    adapter.notifyItemChanged(position)
                                    findNavController().popBackStack(R.id.mainFragment, false)
                                }
                            })
                        }
                        R.id.delete -> {
                            //Delete Dialog Open
                            val dialog =
                                AlertDialog.Builder(root.context, R.style.RoundedCornersDialog)
                            val alertDialog = dialog.create()
                            alertDialog.setCancelable(false)
                            val view = layoutInflater.inflate(R.layout.delete_dialog, null, false)
                            alertDialog.setView(view)
                            view.delete_cancel.setOnClickListener {
                                alertDialog.dismiss()
                            }
                            view.delete_delete.setOnClickListener {
                                bootcampDao?.deleteBootcamp(bootcamp)
                                alertDialog.dismiss()
                                Toast.makeText(root.context, "O'chirildi", Toast.LENGTH_SHORT)
                                    .show()
                                loadData()
                                loadItemAdapter()
                                adapter.notifyItemRemoved(position)
                                adapter.notifyItemRangeChanged(position, data!!.size)
                            }
                            alertDialog.show()
                        }
                    }

                    true
                }

                popupMenu.show()
            }
        })
    }

    private fun loadItemAdapter() {
        adapter.setAdapter(data!!)
        root.rv.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        loadData()
        loadItemAdapter()
    }

}