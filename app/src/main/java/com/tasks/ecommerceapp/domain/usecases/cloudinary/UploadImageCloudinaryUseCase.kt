package com.tasks.ecommerceapp.domain.usecases.cloudinary

import android.content.Context
import android.net.Uri
import android.util.Log
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import javax.inject.Inject
import com.tasks.ecommerceapp.common.Constants.CLOUDINARY_API_KEY
import com.tasks.ecommerceapp.common.Constants.CLOUDINARY_API_SECRET
import com.tasks.ecommerceapp.common.Constants.CLOUDINARY_CLOUD_NAME
import com.tasks.ecommerceapp.common.Constants.CLOUDINARY_CLOUD_UPLOAD_PRESET
import com.tasks.ecommerceapp.common.listener.UploadImageCallback

class UploadImageCloudinaryUseCase @Inject constructor() {

   operator fun invoke(
        uri: Uri,
        context: Context,
        callback: UploadImageCallback
    ){

        val config: HashMap<String, String> = HashMap()
        config["cloud_name"] = CLOUDINARY_CLOUD_NAME
        config["api_key"] = CLOUDINARY_API_KEY
        config["api_secret"] = CLOUDINARY_API_SECRET
        MediaManager.init(context, config)


        MediaManager.get().upload(uri).unsigned(CLOUDINARY_CLOUD_UPLOAD_PRESET).callback(object :UploadCallback{
            override fun onStart(requestId: String?) {}
            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {}

            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                if (resultData != null) {
                    val publicId = resultData["public_id"] as String
                    val url = MediaManager.get().url().generate(publicId)
                    callback.onUploadSuccess(url)
                }
            }

            override fun onError(requestId: String?, error: ErrorInfo?) {
                Log.d("ErrorInfo",error?.description.toString())
            }

            override fun onReschedule(requestId: String?, error: ErrorInfo?) {}
        }).dispatch()

   }
}