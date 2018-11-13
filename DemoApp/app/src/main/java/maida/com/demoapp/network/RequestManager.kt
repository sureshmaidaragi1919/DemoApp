package maida.com.demoapp.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import java.io.IOException

class RequestManager {
    companion object {


        fun getData(urlString: String): String {
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url(urlString)
                    .get()
                    .build()

            try {
                val response = client.newCall(request).execute()
                if (response.code() == 200) {
                    val jsonData = response.body()!!.string()
                    return jsonData;
                } else {
                    return ""
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return ""
        }
    }
}