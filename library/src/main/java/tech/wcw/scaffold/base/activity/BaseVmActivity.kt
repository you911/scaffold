package tech.wcw.scaffold.base.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import tech.wcw.scaffold.base.BaseViewModel
import tech.wcw.support.toast
import java.lang.reflect.ParameterizedType

/**
 * @Author: tech_wcw
 * @Eamil tech_wcw@163.com
 * @Data: 2021/2/3 18:38
 * @Description:
 */
open abstract class BaseVmActivity<VM : BaseViewModel> : BasicActivity() {
    lateinit var mViewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = initViewModel()
        mViewModel.injectLifecycle(this)
        uiObserver()
        createView()
        initView(savedInstanceState)
        initData()
    }

    open fun createView() {

    }

    open fun uiObserver() {
        mViewModel.toast.observe(this) {
            toast(it)
        }
        mViewModel.loadingState.observe(this) {
            when (it.enable) {
                true -> {
                    showLoading(it.msg)
                }
                else -> {
                    dismissLoading()
                }
            }
        }
    }

    open fun dismissLoading(){

    }

    open fun showLoading(msg: String?){

    }

    abstract fun initView(savedInstanceState: Bundle?)

    open fun initViewModel(): VM {
        val type = javaClass.genericSuperclass
        mViewModel = if (type is ParameterizedType) {
            ViewModelProvider(
                this
            )[type.actualTypeArguments[0] as Class<VM>]
        } else {
            ViewModelProvider(
                this
            )[BaseViewModel::class.java as Class<VM>]
        }
        return mViewModel;
    }


    abstract fun initData()

}

