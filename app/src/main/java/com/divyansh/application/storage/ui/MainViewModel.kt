package com.divyansh.application.storage.ui

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.divyansh.application.storage.workers.UploadWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

const val KEY_IMAGE_URI = "KEY_image_uri"

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application
) : ViewModel() {
    private val TAG = "MainViewModel"

    private val workManager = WorkManager.getInstance(application)
    fun uploadImageRequestBuilder(uri: Uri?) {
        uri?.let {
            val request =
                OneTimeWorkRequestBuilder<UploadWorker>().setInputData(uriInputDataBuilder(uri)).build()
            workManager.enqueue(request)
        }

    }

    private fun uriInputDataBuilder(uri: Uri): Data {
        return Data.Builder().putString(KEY_IMAGE_URI, uri.toString()).build()
    }
}