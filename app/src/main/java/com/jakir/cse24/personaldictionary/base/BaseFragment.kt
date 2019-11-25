package com.jakir.cse24.personaldictionary.base

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.jakir.cse24.personaldictionary.R
import kotlinx.android.synthetic.main.layout_progress_dialog.view.*

/**
 * A simple [Fragment] subclass.
 * Created by Md. Jakir Hossain on 02/05/2019.
 */
abstract class BaseFragment : Fragment() {
    private var dialog: AlertDialog? = null

    /**
     * This method is for printing a ERROR log message on the system console
     * Created by Md. Jakir Hossain on 02/05/2019.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    protected fun logE(tag: String, msg: String) {
        Log.e(tag, msg)
    }

    /**
     * This method is for printing a DEBUG log message on the system console
     * Created by Md. Jakir Hossain on 02/05/2019.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    protected fun logD(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    /**
     * This method is for showing message using android {@link Toast}
     * Created by Md. Jakir Hossain on 29/04/2019.
     *
     * @param msg message which will display on screen
     */
    protected fun showToast(msg: String) {
        val toast: Toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    protected fun showSnack(msg: String){
        val snack= Snackbar.make(this.view!!, msg, Snackbar.LENGTH_LONG)
        snack.show()
    }

    /**
     * This method is for showing error/warning using alert dialog.
     * Created by Md. Jakir Hossain on 29/04/2019.
     *
     * @param title alert title
     * @param msg   alert message
     * @param icon  alert icon
     */
    protected fun showAlert(title: String, msg: String, icon: Int) {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        dialog.setTitle(title)
        dialog.setIcon(icon)
        dialog.setMessage(msg)
        dialog.setPositiveButton(
            getString(android.R.string.ok)
        ) { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        dialog.show()
    }

    /**
     * This method is for showing error/warning using alert dialog.
     * Created by Md. Jakir Hossain on 29/04/2019.
     * <p>
     * {@link BaseActivity#showAlert(String, String, int)}  supper method}
     *
     * @param msg error/warning message
     */
    protected fun showAlert(msg: String) {
        showAlert("Dictionary", msg, android.R.drawable.ic_dialog_alert)
    }

    /**
     * This method is for showing progress with custom message(optional).
     * Created by Md. Jakir Hossain on 07/11/2019.
     *
     * @param msg progress message(optional)
     */
    protected fun showProgressDialog(msg: String = "") {
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_progress_dialog, null)
        val alert = AlertDialog.Builder(requireContext())
        alert.setCancelable(false)
        alert.setView(view)
        if (dialog != null) {
            dialog!!.dismiss()
        }
        dialog = alert.create()
        if (TextUtils.isEmpty(msg)) {
            view.tvProgressMessage.visibility = View.GONE
            dialog!!.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)
        } else
            view.tvProgressMessage.text = msg

        dialog!!.show()
    }

    /**
     * This method is for hide/clear progress dialog
     * Created by Md. Jakir Hossain on 07/11/2019.
     */
    protected fun hideProgressDialog(){
        if (dialog != null) {
            dialog!!.dismiss()
            dialog = null
        }
    }

}