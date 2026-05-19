package com.apay.comprobante

import android.content.Context
import android.net.Uri
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest

@HiltWorker
class UploadWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val storage: FirebaseStorage,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val imageUriStr = inputData.getString(KEY_IMAGE_URI) ?: return Result.failure()
        val socioId     = inputData.getString(KEY_SOCIO_ID)  ?: return Result.failure()
        val imageUri    = Uri.parse(imageUriStr)

        return try {
            val hash     = calcularHash(imageUriStr)
            val fileName = "${socioId}_${System.currentTimeMillis()}.jpg"
            val ref      = storage.reference.child("comprobantes/$socioId/$fileName")

            ref.putFile(imageUri).await()
            val downloadUrl = ref.downloadUrl.await().toString()

            firestore.collection("transacciones").add(
                hashMapOf(
                    "socioId"         to socioId,
                    "comprobanteUrl"  to downloadUrl,
                    "comprobanteHash" to hash,
                    "estado"          to "PENDING",
                    "carril"          to "MANUAL",
                    "timestamp"       to FieldValue.serverTimestamp()
                )
            ).await()

            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 3) Result.retry() else Result.failure()
        }
    }

    private fun calcularHash(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    companion object {
        const val KEY_IMAGE_URI = "image_uri"
        const val KEY_SOCIO_ID  = "socio_id"

        fun construir(imageUri: Uri, socioId: String): OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<UploadWorker>()
                .setInputData(workDataOf(
                    KEY_IMAGE_URI to imageUri.toString(),
                    KEY_SOCIO_ID  to socioId
                ))
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    15,
                    java.util.concurrent.TimeUnit.SECONDS
                )
                .setConstraints(Constraints(requiredNetworkType = NetworkType.CONNECTED))
                .build()
    }
}
