package co.heri.monzo.dialods

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import co.heri.monzo.R
import kotlinx.android.synthetic.main.request_money_dialog.*


class RequestDialog : DialogFragment() {


    private var toolbar: Toolbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar!!.setNavigationOnClickListener({ v -> dismiss() })
        //toolbar!!.setTitle("Some Title")
        //toolbar!!.inflateMenu(R.menu.example_dialog)
        toolbar!!.setOnMenuItemClickListener { item ->
            dismiss()
            true
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.request_money_dialog, container, false)

        toolbar = view.findViewById(R.id.toolbar)

        return view
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window.setLayout(width, height)
             dialog.window.setWindowAnimations(R.style.AppTheme_Slide)
        }
    }


    fun setNumber(view: View){
        var tag = resources.getResourceEntryName(view.id)

        var text = amount_txt.text.toString()

        var new_number = "0";

        if(tag == "backspace_btn"){
            val new_text: String? = text.take(text.length - 1)
            if (new_text != null) {
                if (new_text.isNotEmpty()){
                    new_number = new_text
                }
            }

        } else {

            val extracted_numbers: List<String>? = tag.split("_")

            if (extracted_numbers != null) {
                if(text.toInt() == 0){
                    if(extracted_numbers.last().toInt() != 0){
                        new_number = extracted_numbers.last()
                    }

                }else {
                    new_number = text.plus(extracted_numbers.last())
                }

            }
        }

        amount_txt.text = new_number;

    }



    companion object {

        val TAG = "request_dialog"

        fun display(fragmentManager: FragmentManager): RequestDialog {
            val dialog = RequestDialog()
            dialog.show(fragmentManager, TAG)
            return dialog
        }
    }
}