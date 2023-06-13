package tech.wcw.simple.ui.fragment

import android.os.Bundle
import tech.wcw.scaffold.base.fragment.BaseVmDbFragment
import tech.wcw.simple.R
import tech.wcw.simple.databinding.FragmentSettingBinding
import tech.wcw.simple.vm.DataViewModel

class SettingFragment : BaseVmDbFragment<DataViewModel, FragmentSettingBinding>() {
    override fun layoutId(): Int {
        return R.layout.fragment_setting
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBind.tvLabel.text = "SettingFragment"
    }

    override fun initData() {

    }
}