package com.jakir.cse24.personaldictionary.data.repositories

import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.jakir.cse24.personaldictionary.data.model.AppInfo
import okhttp3.*
import java.io.IOException

class AppInfoRepository {
    fun getAppInfo(): MutableLiveData<AppInfo> {
        val res = MutableLiveData<AppInfo>()
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://gitlab.com/jakir123/personaldictionary/-/raw/master/app_info.json")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // TODO: Need to fix
//                EasyLog.logE(
//                    "Exception in getting app info: ${e.localizedMessage}",
//                    "AppInfoRepository"
//                )
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                val appInfo = gson.fromJson(body, AppInfo::class.java)
                res.postValue(appInfo)
            }

        })
        return res
    }
}