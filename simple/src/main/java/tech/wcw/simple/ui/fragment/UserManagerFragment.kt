package tech.wcw.simple.ui.fragment

import android.os.Bundle
import tech.wcw.scaffold.base.fragment.BaseVmDbFragment
import tech.wcw.simple.R
import tech.wcw.simple.databinding.FragmentUserManagerBinding
import tech.wcw.simple.vm.UserManagerViewModel

class UserManagerFragment : BaseVmDbFragment<UserManagerViewModel, FragmentUserManagerBinding>() {
    override fun layoutId(): Int {
        return R.layout.fragment_user_manager
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {

    }

}