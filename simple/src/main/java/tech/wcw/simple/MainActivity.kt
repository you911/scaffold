package tech.wcw.simple

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import tech.wcw.scaffold.base.activity.BaseVmDbActivity
import tech.wcw.simple.databinding.ActivityMainBinding
import tech.wcw.simple.vm.MainViewModel
import tech.wcw.support.os.LifecycleHandler


class MainActivity : BaseVmDbActivity<MainViewModel, ActivityMainBinding>(){
    private lateinit var mNavController: NavController
    lateinit var handler: LifecycleHandler
    override fun initView(savedInstanceState: Bundle?) {
        handler = LifecycleHandler(lifecycleOwner = this, Looper.getMainLooper())
        mNavController =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!.findNavController()
        mBind.bottomNav.setupWithNavController(mNavController)
    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        startActivity(intent)
    }

    override fun initData() {
        mViewModel.user.observe(this) {
            Log.e(TAG, "initData: $it")
            if (it == null) {
                mNavController.navigate(R.id.login_fragment)
            }
        }

    }

    override fun dismissLoading() {
        Log.i(TAG, "弹窗消失")
    }

    override fun showLoading(msg: String?) {
        Log.i(TAG, "弹窗 $msg")
    }

}
