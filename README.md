# Firebase-cloud-storage-WorkManager-sample
Upload images to Firebase cloud storage using WorkManager

- Uses ```CoroutineWorker``` to upload images in the background thread.
- Hilt for depenpency injection -  ```@HiltWorker``` and ```@Assisted``` annotations are used.
- MVVM architecture


#### This is where the magic happens-
```
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
    
    ...
}
```
## Look and feel of the app:

![](https://github.com/DivS-15/Firebase-cloud-storage-WorkManager-sample/blob/master/wm%20storage%20pic.png)

