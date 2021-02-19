package com.htf.diva.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.htf.diva.netUtils.Constants.BROADCAST_ACTION
import com.htf.diva.netUtils.Constants.KEY_PREF_USER_LANGUAGE
import com.htf.diva.utils.*
import java.util.*


@Suppress("DEPRECATION")
abstract class BaseDarkActivity<T : ViewDataBinding, V : BaseViewModel>(private val viewModelClass: Class<V>) :
    AppCompatActivity(), LifecycleObserver {
    protected var mDialCode: String = "966"
    lateinit var viewModel: V
    lateinit var binding: T
    open val layout: Int = -1
    open val initializeViewModel: V.() -> Unit = {}
    var progressDialog: Dialog? = null
    private val mReceiver: AppBroadCastReceiver = AppBroadCastReceiver()
    val currUser= AppPreferences.getInstance(MyApplication.getAppContext()).getUserDetails()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        }

        setLocale()
        progressDialog = DialogUtils.showProgress(this)
        if (progressDialog?.isShowing!!)
            progressDialog?.hide()

        viewModel = obtainViewModel<V>()
        initializeViewModel(viewModel)
        bind<T>(layout)
        observerViewModel(viewModel.isInternetOn,this::onHandleInternetOnResult)
    }

    private fun onHandleInternetOnResult(isInternetOn:Boolean){
        if (!isInternetOn)
            DialogUtils.showNoInternetDialog(this,null,12345)
    }


    private fun <V : BaseViewModel> obtainViewModel() =
        ViewModelProvider(this).get(viewModelClass).apply {
            lifecycle.addObserver(this@BaseDarkActivity)
        }


    private fun <C : ViewDataBinding> bind(layout: Int) {
        binding = DataBindingUtil.setContentView<T>(this, layout).apply {
            lifecycleOwner = this@BaseDarkActivity
            setVariable(layout, viewModel)
            executePendingBindings()
        }


    }

    protected inline fun <reified T : ViewModel> androidx.fragment.app.Fragment.getViewModel(
        noinline creator: (() -> T)? = null
    ): T {
        return if (creator == null)
            ViewModelProvider(this).get(T::class.java)
        else
            ViewModelProvider(this, BaseViewModelFactory(creator)).get(T::class.java)
    }

    protected inline fun <reified T : ViewModel> FragmentActivity.getViewModel(noinline creator: (() -> T)? = null): T {
        return if (creator == null)
            ViewModelProvider(this).get(T::class.java)
        else
            ViewModelProvider(this, BaseViewModelFactory(creator)).get(T::class.java)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {

        val v = currentFocus

        if (v != null &&
            (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) &&
            v is EditText &&
            !v.javaClass.name.startsWith("android.webkit.")
        ) {
            val scrcoords = IntArray(2)
            v.getLocationOnScreen(scrcoords)
            val x = ev.rawX + v.left - scrcoords[0]
            val y = ev.rawY + v.top - scrcoords[1]

            if (x < v.left || x > v.right || y < v.top || y > v.bottom)
                DialogUtils.hideKeyboard(this)
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left)
    }

    override fun onDestroy() {
        super.onDestroy()
        progressDialog?.dismiss()
    }


    protected fun setLocale() {
        val res = baseContext.resources
        val dm = res.displayMetrics
        val conf = res.configuration
        val savedLanguage = AppPreferences.getInstance(this).getFromPreference(KEY_PREF_USER_LANGUAGE)
        AppSession.deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        if (savedLanguage != null) {
            when (savedLanguage) {
                "en" -> {
                    conf.setLocale(Locale("en"))
                    AppSession.locale = "en"
                    AppSession.isLocaleEnglish = true

                }
                "ar" -> {
                    conf.setLocale(Locale("ar"))
                    AppSession.locale = "ar"
                    AppSession.isLocaleEnglish = false
                }
                else -> {
                    conf.setLocale(Locale("ar"))
                    AppSession.locale = "ar"
                    AppSession.isLocaleEnglish = false
                }
            }
            res.updateConfiguration(conf, dm)
//            Toast.makeText(this, AppSession.locale, Toast.LENGTH_SHORT).show()
        }
    }



    override fun onResume() {
        super.onResume()
        try {
            registerBroadCastReceiver()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun registerBroadCastReceiver() {
        val filter = IntentFilter()
        filter.addAction(BROADCAST_ACTION)
        filter.addAction("user_black_listed")
        registerReceiver(mReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        try {
            unregisterReceiver(mReceiver)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    open fun hideKeyboard() {
        try {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(this.window.currentFocus?.windowToken, 0)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun onBackBtnClick(view:View){
        finish()
    }




}