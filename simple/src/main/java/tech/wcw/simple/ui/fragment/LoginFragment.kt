package tech.wcw.simple.ui.fragment

import android.os.Bundle
import tech.wcw.scaffold.base.fragment.BaseVmDbFragment
import tech.wcw.simple.R
import tech.wcw.simple.databinding.FragmentLoginBinding
import tech.wcw.simple.vm.LoginViewModel

class LoginFragment : BaseVmDbFragment<LoginViewModel, FragmentLoginBinding>() {
    override fun layoutId(): Int {
        return R.layout.fragment_login
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData() {

    }
}