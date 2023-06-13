package tech.wcw.scaffold.base.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import tech.wcw.support.os.OnActivityResultListener
import tech.wcw.support.os.ResultCallBack

/**
 * @Author: tech_wcw
 * @Eamil tech_wcw@163.com
 * @Data: 2021/2/3 18:38
 * @Description:
 */
open abstract class BasicActivity : AppCompatActivity() {
    val TAG: String = javaClass.simpleName
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var permissionCallBack: ResultCallBack<Boolean> = ResultCallBack<Boolean>()
    lateinit var uriLauncher: ActivityResultLauncher<String>
    private var uriCallBack: ResultCallBack<Uri?> = ResultCallBack<Uri?>()
    lateinit var launcher: ActivityResultLauncher<Intent>
    private var onActivityResultListener: ResultCallBack<ActivityResult> =
        ResultCallBack<ActivityResult>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configBar(
            show = !fullScreen(),
            darkIcon = true,
            color = ContextCompat.getColor(this, android.R.color.white)
        )
        initLauncher()
    }

    private fun initLauncher() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
            permissionCallBack
        )
        uriLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent(), uriCallBack)
        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            onActivityResultListener
        )
    }

    fun launcherForPermission(permission: String, callBack: OnActivityResultListener<Boolean>) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            callBack.onActivityResult(true)
        } else {
            permissionCallBack.listener = callBack
            permissionLauncher.launch(permission)
        }
    }

    fun runOnPermission(permission: String, block: () -> Unit) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            block()
        } else {
            permissionCallBack.listener = object : OnActivityResultListener<Boolean> {
                override fun onActivityResult(result: Boolean) {
                    if (ContextCompat.checkSelfPermission(
                            this@BasicActivity,
                            permission
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        block()
                    }
                }
            }
            permissionLauncher.launch(permission)
        }
    }

    //        launch("image/*")
    //        launch("video/*")
    //        launch("audio/*")
    //        launch("text/plain")
    //        launch("application/pdf")
    //        launch("application/msword")
    fun launcherForUri(type: String, callBack: OnActivityResultListener<Uri?>) {
        uriCallBack.listener = callBack
        uriLauncher.launch(type)
    }

    fun launcher(intent: Intent, callBack: OnActivityResultListener<ActivityResult>) {
        onActivityResultListener.listener = callBack
        launcher.launch(intent)
    }

    fun launcherForCamera(uri: Uri, callBack: OnActivityResultListener<ActivityResult>) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        launcher(intent, callBack)
    }

    /**
     * If full screen is required, rewrite fullScreen and return true
     */
    open fun configBar(
        show: Boolean,
        darkIcon: Boolean = true,
        color: Int = ContextCompat.getColor(this, android.R.color.white)
    ) {
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController.let {
            insetsController.show(WindowInsetsCompat.Type.statusBars())
            insetsController.show(WindowInsetsCompat.Type.navigationBars())
            insetsController.isAppearanceLightStatusBars = darkIcon
            insetsController.isAppearanceLightNavigationBars = darkIcon
            window.statusBarColor = color
            window.navigationBarColor = color
            if (!show) {
                insetsController.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

    open fun fullScreen(): Boolean {
        return false
    }

}

