package uz.revolution.databasebootcampnews.dialogs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_add_dialog.view.*
import kotlinx.android.synthetic.main.fragment_edit_dialog.view.*
import kotlinx.android.synthetic.main.fragment_edit_dialog.view.save
import uz.revolution.databasebootcampnews.R
import uz.revolution.databasebootcampnews.adapters.SpinnerAdapter
import uz.revolution.databasebootcampnews.databinding.FragmentEditDialogBinding
import uz.revolution.databasebootcampnews.models.Bootcamp
import uz.revolution.databasebootcampnews.models.Category


private const val ARG_PARAM1 = "edit_bootcamp"
private const val ARG_PARAM2 = "param2"

class EditDialog : DialogFragment() {

    private var param1: Bootcamp? = null
    private var param2: ArrayList<Category>? = null
    private var onSaveClick: OnSaveClick? = null
    private var spinnerAdapter: SpinnerAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Bootcamp
            param2 = it.getSerializable(ARG_PARAM2) as ArrayList<Category>
        }
    }

    lateinit var root:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = FragmentEditDialogBinding.inflate(layoutInflater).root
        loadDataToView()
        setSpinner()
        onSaveClick()
        onCancelClick()
        return root
    }

    private fun onCancelClick() {
        root.cancel_edit.setOnClickListener{
            dismiss()
        }
    }

    private fun setSpinner() {
        spinnerAdapter=SpinnerAdapter(param2!!)
        root.edit_bootcamp_spinner.adapter=spinnerAdapter
        root.edit_bootcamp_spinner.setSelection(param1?.categoryID!! - 1)
    }

    private fun onSaveClick() {
        root.save.setOnClickListener {
            val title=root.edit_bootcamp_title.text.toString().trim()
            val text=root.edit_bootcamp_text.text.toString().trim()
            val categoryID=param2!![root.edit_bootcamp_spinner.selectedItemPosition].id

            if (title.isNotEmpty() && text.isNotEmpty()) {
                if (onSaveClick != null) {
                    onSaveClick!!.onClick(Bootcamp(title, text, categoryID))
                }
                dismiss()
            } else {
                Toast.makeText(root.context, "Bo'sh joylarni to'ldiring", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadDataToView() {
        root.edit_bootcamp_title.setText(param1?.title.toString())
        root.edit_bootcamp_text.setText(param1?.text.toString())
    }

    override fun getTheme(): Int = R.style.RoundedCornersDialog

    interface OnSaveClick {
        fun onClick(bootcamp: Bootcamp)
    }

    fun setOnSaveClick(onSaveClick: OnSaveClick) {
        this.onSaveClick = onSaveClick
    }

    companion object {
        @JvmStatic
        fun     newInstance(param1: Bootcamp, param2: ArrayList<Category>) =
            EditDialog().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                    putSerializable(ARG_PARAM2, param2)
                }
            }
    }
}