package tech.wcw.scaffold.base.fragment

import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.fragment.app.Fragment
import tech.wcw.scaffold.base.activity.BasicActivity
import tech.wcw.support.os.OnActivityResultListener

/**
 * @Author: tech_wcw
 * @Eamil tech_wcw@163.com
 * @Data: 2021/2/3 18:41
 * @Description:
 */
abstract class BasicFragment : Fragment() {
    fun requestPermission(permission: String, callBack: OnActivityResultListener<Boolean>) {
        val requireActivity = requireActivity() as BasicActivity
        requireActivity.launcherForPermission(permission, callBack)
    }

    fun launcherForUri(type: String, callBack: OnActivityResultListener<Uri?>) {
        val requireActivity = requireActivity() as BasicActivity
        requireActivity.launcherForUri(type, callBack)
    }

    fun launcher(intent: Intent, callBack: OnActivityResultListener<ActivityResult>) {
        val requireActivity = requireActivity() as BasicActivity
        requireActivity.launcher(intent, callBack)
    }

    fun launcherForCamera(uri: Uri, callBack: OnActivityResultListener<ActivityResult>) {
        val requireActivity = requireActivity() as BasicActivity
        requireActivity.launcherForCamera(uri, callBack)
    }

    fun runOnPermission(permission: String, block: () -> Unit) {
        val requireActivity = requireActivity() as BasicActivity
        requireActivity.runOnPermission(permission, block)
    }
}