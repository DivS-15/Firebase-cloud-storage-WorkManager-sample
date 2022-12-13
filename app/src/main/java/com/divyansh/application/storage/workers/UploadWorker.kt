package com.divyansh.application.storage.workers

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.divyansh.application.storage.ui.KEY_IMAGE_URI
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException

@HiltWorker
class UploadWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted workerParameters: WorkerParameters,
    private val ioDispatcher: CoroutineDispatcher
) : CoroutineWorker(ctx, workerParameters) {

    private val storageReference = Firebase.storage.reference.child("user_images")

    override suspend fun doWork(): Result = withContext(ioDispatcher) {

        val inputFileUri = inputData.getString(KEY_IMAGE_URI)
        return@withContext try {
            //Parse the inputData's string to Uri
            val inputUri = Uri.parse(inputFileUri)

            //Use the Uri to perform upload/download
            uploadImageFromUri(inputUri)
        } catch (exception: Exception) {
            exception.printStackTrace()
            Result.failure()
        } catch (e: IOException) {
            e.printStackTrace()
            Result.failure()
        }
    }

    private fun uploadImageFromUri(fileUri: Uri): Result {
        fileUri.lastPathSegment?.let {
            //create a child node using lastPathSegment
            val photoRef = storageReference.child(it)

            Log.d("Upload Worker", it)

            //Upload using the complete file-Uri
            photoRef.putFile(fileUri).addOnSuccessListener {

            }
        }
        return Result.success()
    }
}