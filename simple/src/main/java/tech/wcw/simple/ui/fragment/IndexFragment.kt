package tech.wcw.simple.ui.fragment

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.core.content.FileProvider
import tech.wcw.scaffold.base.fragment.BaseVmDbFragment
import tech.wcw.simple.databinding.FragmentIndexBinding
import tech.wcw.simple.vm.IndexViewModel
import tech.wcw.simple.R
import tech.wcw.simple.BR
import tech.wcw.simple.MainActivity
import tech.wcw.support.os.OnActivityResultListener
import tech.wcw.support.utils.NotifyUtils
import java.io.File

class IndexFragment : BaseVmDbFragment<IndexViewModel, FragmentIndexBinding>() {
    override fun layoutId(): Int {
        return R.layout.fragment_index
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBind.setVariable(BR.vm, mViewModel)
        mBind.btNotify.setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(requireContext(), 0, intent, FLAG_UPDATE_CURRENT)
            NotifyUtils.show(
                requireActivity(),
                "您有一条新消息",
                "将有一颗小行星与地球擦肩而过",
                "Notify",
                pendingIntent = pendingIntent
            )
        }
        mViewModel.photo.observe(viewLifecycleOwner) {
            it?.let {
                mBind.ivPhoto.visibility = View.VISIBLE
                mBind.ivPhoto.setImageBitmap(
                    BitmapFactory.decodeStream(
                        requireActivity().contentResolver.openInputStream(
                            it
                        )
                    )
                )
            }

        }
        mBind.btAlbum.setOnClickListener {
            val innerIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            launcher(innerIntent, object : OnActivityResultListener<ActivityResult> {
                override fun onActivityResult(result: ActivityResult) {
                    result.data?.let {
                        var uri = it.data
                        mViewModel.photo.postValue(uri)
                    }

                }
            })
        }
        mBind.btCamera.setOnClickListener {
            runOnPermission(Manifest.permission.CAMERA) { openCamera() }
//            requestPermission(
//                Manifest.permission.CAMERA,
//                object : OnActivityResultListener<Boolean> {
//                    override fun onActivityResult(result: Boolean) {
//                        openCamera()
//                    }
//                })
        }
        mBind.btFile.setOnClickListener {
            launcherForUri("video/*", object : OnActivityResultListener<Uri?> {
                override fun onActivityResult(result: Uri?) {
                    result?.let {
                        mBind.tvFileDes.visibility = View.VISIBLE
                        mBind.tvFileDes.text = result.toString()
                    }
                }

            })
        }

        mBind.btDownload.setOnClickListener {
            mViewModel.download(url = "https://img0.baidu.com/it/u=4162443464,2854908495&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500")
        }
    }

    private fun openCamera() {
        val uri =
            FileProvider.getUriForFile(
                requireContext(),
                requireContext().packageName + ".fileprovider",
                File(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.path + File.separator + "/test.jpg")
            )
        launcherForCamera(uri, object : OnActivityResultListener<ActivityResult> {
            override fun onActivityResult(result: ActivityResult) {
                if (result.resultCode == Activity.RESULT_OK) {
                    mViewModel.photo.postValue(uri)
                }
            }
        })
    }


    override fun initData() {
        mViewModel.info.observe(this) {
            it?.let {
                val buffer = StringBuffer()
                it.forEach { item ->
                    buffer.append("${item.title}\n")
                    buffer.append("---------------------\n")
                }
                mBind.tvInfo.text = buffer.toString()
            }
        }
    }
}