package tech.wcw.simple.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import tech.wcw.scaffold.base.BaseViewModel
import tech.wcw.simple.model.User

class MainViewModel : BaseViewModel() {
    var user: MutableLiveData<User> = MutableLiveData(User("", ""))

    val TAG = "MainViewModel"
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate")
    }


    override fun onPause() {
        Log.i(TAG, "onPause")
    }

    override fun onStart() {
        Log.i(TAG, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.i(TAG, "onResume")
    }

    override fun onStop() {
        Log.i(TAG, "onStop")
    }


}