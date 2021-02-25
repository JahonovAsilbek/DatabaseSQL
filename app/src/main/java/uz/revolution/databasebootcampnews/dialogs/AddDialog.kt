package uz.revolution.databasebootcampnews.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_add_dialog.view.*
import uz.revolution.databasebootcampnews.R
import uz.revolution.databasebootcampnews.adapters.SpinnerAdapter
import uz.revolution.databasebootcampnews.databinding.FragmentAddDialogBinding
import uz.revolution.databasebootcampnews.models.Bootcamp
import uz.revolution.databasebootcampnews.models.Category

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddDialog : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: ArrayList<Category>? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as ArrayList<Category>
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var root: View
    private var onSaveClick: OnSaveClick? = null
    private var spinnerAdapter:SpinnerAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = FragmentAddDialogBinding.inflate(layoutInflater).root
        isCancelable = false
        setSpinner()
        onSaveClick()
        onCancelClick()

        return root
    }

    private fun setSpinner() {
        spinnerAdapter=SpinnerAdapter(param1!!)
        root.add_bootcamp_spinner.adapter=spinnerAdapter
    }

    private fun onCancelClick() {
        root.cancel.setOnClickListener {
            dismiss()
        }
    }

    private fun onSaveClick() {
        root.save.setOnClickListener {
            val title = root.add_bootcamp_title.text.toString()
            val text = root.add_bootcamp_text.text.toString()
            val categoryID=param1!![root.add_bootcamp_spinner.selectedItemPosition].id

            Log.d("AAAA", "onSaveClick: $categoryID")

            if (title.isNotEmpty() && text.isNotEmpty()) {
                if (onSaveClick != null) {
                    onSaveClick!!.onClick(Bootcamp(title, text, categoryID))
                }
                dismiss()
            } else {
                Toast.makeText(root.context, "Bo'sh joylarni to'ldiring!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
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
        fun newInstance(param1: ArrayList<Category>, param2: String) =
            AddDialog().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}