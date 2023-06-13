package tech.wcw.scaffold.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import tech.wcw.scaffold.base.BaseViewModel
import tech.wcw.scaffold.base.activity.BaseVmActivity
import tech.wcw.support.toast
import java.lang.reflect.ParameterizedType

/**
 * @Author: tech_wcw
 * @Eamil tech_wcw@163.com
 * @Data: 2021/2/3 18:41
 * @Description:
 */
abstract class BaseVmFragment<VM : BaseViewModel> : BasicFragment() {
    lateinit var mViewModel: VM
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = createView()
        mViewModel = initViewModel()
        mViewModel.injectLifecycle(viewLifecycleOwner)
        uiObserver()
        initView(savedInstanceState)
        initData()
        return view
    }

    abstract fun createView(): View

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun initData()

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

    open fun uiObserver() {
        mViewModel.toast.observe(viewLifecycleOwner) {
            requireActivity().toast(it)
        }
        mViewModel.loadingState.observe(viewLifecycleOwner) {
            when (it.enable) {
                true -> {
                    (requireActivity() as BaseVmActivity<*>).showLoading(it.msg)
                }
                else -> {
                    (requireActivity() as BaseVmActivity<*>).dismissLoading()
                }
            }
        }
    }
}