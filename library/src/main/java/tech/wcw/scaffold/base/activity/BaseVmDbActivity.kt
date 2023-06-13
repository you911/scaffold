package tech.wcw.scaffold.base.activity

import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import tech.wcw.scaffold.base.BaseViewModel

/**
 * @Author: tech_wcw
 * @Eamil tech_wcw@163.com
 * @Data: 2021/2/3 18:38
 * @Description:
 */
open abstract class BaseVmDbActivity<VM : BaseViewModel, DB : ViewDataBinding> :
    BaseVmActivity<VM>() {
    lateinit var mBind: DB
    
    override fun createView() {
        mBind = DataBindingUtil.setContentView(this, layoutId())
        mBind.lifecycleOwner = this
    }

    @LayoutRes
    abstract fun layoutId(): Int

    override fun onDestroy() {
        super.onDestroy()
        mBind.unbind()
    }
}

