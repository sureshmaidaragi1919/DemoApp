package maida.com.demoapp.util

import android.content.Context
import android.net.ConnectivityManager
import maida.com.demoapp.R
import maida.com.demoapp.view.BaseActivity

class CommonUtils {
    companion object {

        /**
         * Checks for available internet connection
         */
        fun isInternetAvailable(ctx: Context): Boolean {
            val result = false
            val conMgr = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return if (conMgr.activeNetworkInfo != null) {
                conMgr.activeNetworkInfo.isConnected
            } else {

                 result
            }
        }
    }
}