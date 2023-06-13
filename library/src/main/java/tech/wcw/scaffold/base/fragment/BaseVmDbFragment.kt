package tech.wcw.scaffold.base.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import tech.wcw.scaffold.base.BaseViewModel

/**
 * @Author: tech_wcw
 * @Eamil tech_wcw@163.com
 * @Data: 2021/2/3 18:41
 * @Description:
 */
abstract class BaseVmDbFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmFragment<VM>() {
    lateinit var mBind: DB

    override fun createView(): View {
        mBind =
            DataBindingUtil.inflate(LayoutInflater.from(requireContext()), layoutId(), null, false)
        mBind.lifecycleOwner = viewLifecycleOwner
        return mBind.root
    }

    abstract fun layoutId(): Int

    override fun onDestroyView() {
        super.onDestroyView()
        mBind.unbind()
    }

}