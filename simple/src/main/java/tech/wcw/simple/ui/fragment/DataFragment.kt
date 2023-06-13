package tech.wcw.simple.ui.fragment

import android.os.Bundle
import tech.wcw.scaffold.base.fragment.BaseVmDbFragment
import tech.wcw.simple.R
import tech.wcw.simple.databinding.FragmentDataBinding
import tech.wcw.simple.vm.DataViewModel

class DataFragment : BaseVmDbFragment<DataViewModel, FragmentDataBinding>() {
    override fun layoutId(): Int {
        return R.layout.fragment_data
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBind.tvLabel.text = "DataFragment"
    }

    override fun initData() {

    }
}