package maida.com.demoapp.threads

import android.content.Context
import android.os.Handler
import android.os.Message
import maida.com.demoapp.network.RequestManager
import maida.com.demoapp.util.Constants
import java.io.Serializable

class GetCountryFactsThread() : Thread(),Serializable{
/*
Thread class with handler to make API request
*/
    var mHandler: Handler? = null
    var mContext: Context? = null

    constructor(mContext: Context, mHandler: Handler) : this() {
        this.mContext = mContext
        this.mHandler = mHandler

    }

    companion object {

        val GETCOUNTRYFACTSTHREAD_SUCCESS = 1
        val GETCOUNTRYFACTSTHREAD_FAIL = -1
    }

    override fun run() {

        var countryFactRespo = RequestManager.getData(Constants.GETCOUNTRYFACT)
        if (!countryFactRespo.isNullOrEmpty()) sendSuccess(countryFactRespo) else {
            sendFailure()
        }
    }

    fun sendSuccess(respString: String) {

        var msg = Message()
        msg.what = GETCOUNTRYFACTSTHREAD_SUCCESS
        msg.obj = respString
        mHandler!!.sendMessage(msg)
    }

    fun sendFailure() {
        mHandler!!.sendEmptyMessage(GETCOUNTRYFACTSTHREAD_FAIL)
    }


}