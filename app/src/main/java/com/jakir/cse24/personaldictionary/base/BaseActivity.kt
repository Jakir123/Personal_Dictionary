package com.jakir.cse24.personaldictionary.base

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        onViewReady(savedInstanceState)
    }

    /**
     * This abstract method is for getting layout resource ID from every activity which extend BaseActivity
     * Created by Md. Jakir Hossain on 03/11/2019.
     *
     * @return layout resource ID
     */
    abstract fun getContentView(): Int

    /**
     * This method is for every child activities of BaseActivity and can do what they are want to do in onCreate().
     * Created by Md. Jakir Hossain on 03/11/2019.
     *
     * @param savedInstanceState instance of Bundle of OnCreate.
     */
    abstract fun onViewReady(savedInstanceState: Bundle?)

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
        val toast: Toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
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
        val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
        dialog.setTitle(title)
        dialog.setIcon(icon)
        dialog.setMessage(msg)
        dialog.setPositiveButton(
            getString(android.R.string.ok),
            DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
            })
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
     * This function is for setting actionbar title
     * Created by Md. Jakir Hossain on 03/11/2019.
     * @param title action bar title
     */
    protected fun settingActionBar(title: String) {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = title
    }
}