package com.htf.diva.base

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.htf.diva.utils.AppPreferences
import com.htf.diva.utils.DialogUtils
import com.htf.diva.utils.observerViewModel

abstract class BaseFragment<V : BaseViewModel>(private val viewModelClass: Class<V>) : Fragment(),LifecycleObserver{

    var progressDialog: Dialog? = null
    lateinit var viewModel: V

    open val initializeViewModel: V.() -> Unit = {}

    val currUser= AppPreferences.getInstance(MyApplication.getAppContext()).getUserDetails()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = obtainViewModel<V>()
        progressDialog= DialogUtils.showProgress(requireActivity())
        if (progressDialog?.isShowing!!)
            progressDialog?.hide()
        /*  bind<T>(layout)*/
        initializeViewModel(viewModel)

        observerViewModel(viewModel.isInternetOn,this::onHandleInternetOnResult)
    }

    private fun onHandleInternetOnResult(isInternetOn:Boolean){
        if (!isInternetOn)
            DialogUtils.showNoInternetDialog(requireActivity(),null,12345)

    }

    private fun <V : BaseViewModel> obtainViewModel() =
            ViewModelProvider(this).get(viewModelClass).apply {
                lifecycle.addObserver(this@BaseFragment)
            }

    protected inline fun <reified T : ViewModel> FragmentActivity.getViewModel(noinline creator: (() -> T)? = null): T {
        return if (creator == null)
            ViewModelProvider(this).get(T::class.java)
        else
            ViewModelProvider(this, BaseViewModelFactory(creator)).get(T::class.java)
    }




}