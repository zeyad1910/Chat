package com.route.chatc37.base

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<VB:ViewDataBinding,
        VM:BaseViewModel<*>> :AppCompatActivity(),BaseNavigator {
    lateinit var viewBinding:VB
    lateinit var viewModel:VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this,
            getLayoutId())
        viewModel  = generateViewModel()
    }
    abstract fun getLayoutId():Int
    abstract fun generateViewModel():VM
    var alertDialog: AlertDialog?=null
    var progressDialog: ProgressDialog?=null
    override fun showLoading(message: String) {
        progressDialog = ProgressDialog(this);
        progressDialog?.setMessage(message);
        progressDialog?.show()
    }
    override fun showMessage(message:String,
                             posActionTitle:String?,
                             posAction:OnDialogClickListener?,
                             negActionTitle:String?,
                             negAction:OnDialogClickListener?) {
        val builder =
            AlertDialog.Builder(this)
                .setMessage(message);
        if(posActionTitle!=null){
            builder.setPositiveButton(posActionTitle) {
                    dialogInterface, i ->
                dialogInterface.dismiss()
                posAction?.onClick()
            }
        }
        if(negActionTitle!=null){
            builder.setNegativeButton(negActionTitle) {
                    dialogInterface, i ->
                dialogInterface.dismiss()
                negAction?.onClick()
            }
        }
       alertDialog =  builder.show()
    }
    override fun hideDialoge() {
        alertDialog?.dismiss()
        progressDialog?.dismiss()
        progressDialog=null;
    }
}
interface BaseNavigator{
    fun showLoading(message:String);
    fun hideDialoge();
    fun showMessage(message:String,
    posActionTitle:String?=null,
    posAction:OnDialogClickListener?=null,
     negActionTitle:String?=null,
    negAction:OnDialogClickListener?=null, )
}

open class BaseViewModel<N:BaseNavigator> :ViewModel(){
    var navigator:N?=null
}