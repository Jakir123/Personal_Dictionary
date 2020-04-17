package com.jakir.cse24.personaldictionary.view_model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jakir.cse24.personaldictionary.data.model.AppInfo
import com.jakir.cse24.personaldictionary.data.repositories.AppInfoRepository


class AppInfoViewModel : ViewModel() {
    private val appInfoRepository: AppInfoRepository by lazy { AppInfoRepository() }
    lateinit var appinfo: MutableLiveData<AppInfo>

    fun getAppInfo() {
        appinfo = appInfoRepository.getAppInfo()
    }

}
@BindingAdapter("profileImage")
fun loadImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
        .load(imageUrl).apply(RequestOptions().circleCrop())
        .into(view)
}