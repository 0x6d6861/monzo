package co.heri.monzo.dialods

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import co.heri.monzo.R





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



    companion object {

        val TAG = "request_dialog"

        fun display(fragmentManager: FragmentManager): RequestDialog {
            val exampleDialog = RequestDialog()
            exampleDialog.show(fragmentManager, TAG)
            return exampleDialog
        }
    }
}